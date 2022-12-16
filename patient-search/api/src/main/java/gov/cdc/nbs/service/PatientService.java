package gov.cdc.nbs.service;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.blazebit.persistence.CriteriaBuilderFactory;
import com.blazebit.persistence.querydsl.BlazeJPAQuery;
import com.querydsl.core.types.dsl.BooleanExpression;

import gov.cdc.nbs.config.security.NbsUserDetails;
import gov.cdc.nbs.entity.elasticsearch.ElasticsearchPerson;
import gov.cdc.nbs.entity.elasticsearch.Investigation;
import gov.cdc.nbs.entity.elasticsearch.LabReport;
import gov.cdc.nbs.entity.enums.Race;
import gov.cdc.nbs.entity.enums.RecordStatus;
import gov.cdc.nbs.entity.enums.converter.EthnicityConverter;
import gov.cdc.nbs.entity.enums.converter.InstantConverter;
import gov.cdc.nbs.entity.odse.EntityLocatorParticipation;
import gov.cdc.nbs.entity.odse.EntityLocatorParticipationId;
import gov.cdc.nbs.entity.odse.NBSEntity;
import gov.cdc.nbs.entity.odse.Person;
import gov.cdc.nbs.entity.odse.PersonName;
import gov.cdc.nbs.entity.odse.PersonNameId;
import gov.cdc.nbs.entity.odse.PersonRace;
import gov.cdc.nbs.entity.odse.PersonRaceId;
import gov.cdc.nbs.entity.odse.PostalLocator;
import gov.cdc.nbs.entity.odse.QLabEvent;
import gov.cdc.nbs.entity.odse.QOrganization;
import gov.cdc.nbs.entity.odse.QPerson;
import gov.cdc.nbs.entity.odse.TeleLocator;
import gov.cdc.nbs.exception.QueryException;
import gov.cdc.nbs.graphql.GraphQLPage;
import gov.cdc.nbs.graphql.input.PatientInput;
import gov.cdc.nbs.graphql.input.PatientInput.Name;
import gov.cdc.nbs.graphql.input.PatientInput.PhoneNumber;
import gov.cdc.nbs.graphql.input.PatientInput.PhoneType;
import gov.cdc.nbs.graphql.input.PatientInput.PostalAddress;
import gov.cdc.nbs.graphql.searchFilter.EventFilter;
import gov.cdc.nbs.graphql.searchFilter.OrganizationFilter;
import gov.cdc.nbs.graphql.searchFilter.PatientFilter;
import gov.cdc.nbs.repository.PersonRepository;
import gov.cdc.nbs.repository.PostalLocatorRepository;
import gov.cdc.nbs.repository.TeleLocatorRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PatientService {
    @Value("${nbs.max-page-size: 50}")
    private Integer MAX_PAGE_SIZE;

    @PersistenceContext
    private final EntityManager entityManager;
    private final PersonRepository personRepository;
    private final TeleLocatorRepository teleLocatorRepository;
    private final PostalLocatorRepository postalLocatorRepository;
    private final EventService eventService;
    private final CriteriaBuilderFactory criteriaBuilderFactory;
    private final ElasticsearchOperations operations;
    private final InstantConverter instantConverter = new InstantConverter();
    private final EthnicityConverter ethnicityConverter = new EthnicityConverter();

    private <T> BlazeJPAQuery<T> applySort(BlazeJPAQuery<T> query, Sort sort) {
        var person = QPerson.person;
        if (sort == null) {
            // if no sort provided, default sort by lastNm then Id
            return query.orderBy(person.lastNm.asc().nullsLast())
                    .orderBy(person.id.desc().nullsLast());
        }
        var sorts = sort.stream().filter(Objects::nonNull).map(s -> {
            switch (s.getProperty()) {
                case "lastNm":
                    return s.getDirection() == Direction.ASC ? person.lastNm.asc().nullsLast()
                            : person.lastNm.desc().nullsLast();
                case "birthTime":
                    return s.getDirection() == Direction.ASC ? person.birthTime.asc().nullsLast()
                            : person.birthTime.desc().nullsLast();
                case "addTime":
                    return s.getDirection() == Direction.ASC ? person.addTime.asc().nullsLast()
                            : person.addTime.desc().nullsLast();
                default:
                    throw new QueryException("Invalid sort value: " + s.getProperty());
            }
        }).collect(Collectors.toList());
        for (var s : sorts) {
            query = query.orderBy(s);
        }
        // required to have a sort by Id
        return query.orderBy(person.id.desc().nullsLast());
    }

    public Optional<Person> findPatientById(Long id) {
        return personRepository.findById(id);
    }

    public Page<Person> findAllPatients(GraphQLPage page) {
        var person = QPerson.person;
        var pageable = GraphQLPage.toPageable(page, MAX_PAGE_SIZE);
        var query = new BlazeJPAQuery<Person>(entityManager, criteriaBuilderFactory)
                .select(person)
                .from(person);
        query = applySort(query, pageable.getSort());

        var results = query.fetchPage((int) pageable.getOffset(),
                pageable.getPageSize());
        return new PageImpl<Person>(results, pageable, results.getMaxResults());
    }

    public Page<Person> findPatientsByFilter(PatientFilter filter, GraphQLPage page) {
        var pageable = GraphQLPage.toPageable(page, MAX_PAGE_SIZE);
        List<Long> ids;
        long totalCount = 0L;
        BoolQueryBuilder builder = QueryBuilders.boolQuery();

        builder.must(QueryBuilders.matchQuery("cd", "PAT"));

        if (filter.getId() != null) {
            var idQuery = QueryBuilders.boolQuery();
            idQuery.must(QueryBuilders.matchQuery("id", filter.getId()));
            idQuery.must(QueryBuilders.matchQuery("id", generateLocalId(filter.getId())));
            builder.should(idQuery);
        }

        if (filter.getFirstName() != null && !filter.getFirstName().isEmpty()) {
            builder.must(QueryBuilders.wildcardQuery("first_nm", addWildcards(filter.getFirstName())));
        }

        if (filter.getLastName() != null && !filter.getLastName().isEmpty()) {
            builder.must(QueryBuilders.wildcardQuery("last_nm", addWildcards(filter.getLastName())));
        }

        if (filter.getSsn() != null && !filter.getSsn().isEmpty()) {
            builder.must(QueryBuilders.matchQuery("SSN", filter.getSsn()));
        }

        if (filter.getPhoneNumber() != null && !filter.getPhoneNumber().isEmpty()) {
            builder.must(QueryBuilders.matchQuery("hm_phone_nbr", filter.getPhoneNumber()));
        }

        if (filter.getAddress() != null && !filter.getAddress().isEmpty()) {
            builder.must(QueryBuilders.matchQuery("hm_street_addr1", filter.getAddress()));
        }

        if (filter.getGender() != null) {
            builder.must(QueryBuilders.matchQuery("birth_gender_cd", filter.getGender()));
        }

        if (filter.getDeceased() != null) {
            builder.must(QueryBuilders.matchQuery("deceased_ind_cd", filter.getDeceased()));
        }

        if (filter.getCity() != null && !filter.getCity().isEmpty()) {
            builder.must(QueryBuilders.matchQuery("hm_city_desc_txt", filter.getCity()));
        }

        if (filter.getZip() != null && !filter.getZip().isEmpty()) {
            builder.must(QueryBuilders.matchQuery("hm_zip_cd", filter.getZip()));
        }

        if (filter.getState() != null && !filter.getState().isEmpty()) {
            builder.must(QueryBuilders.matchQuery("hm_state_cd", filter.getState()));
        }

        if (filter.getCountry() != null && !filter.getCountry().isEmpty()) {
            builder.must(QueryBuilders.matchQuery("hm_cntry_cd", filter.getCountry()));
        }

        if (filter.getEthnicity() != null) {
            builder.must(QueryBuilders.matchQuery("ethnic_group_ind", ethnicityConverter.write(filter.getEthnicity())));
        }

        if (filter.getRace() != null) {
            builder.must(QueryBuilders.matchQuery("race_desc_txt", filter.getRace()));
        }

        if (filter.getIdentification() != null) {
            builder.must(
                    QueryBuilders.matchQuery("identification", filter.getIdentification().getIdentificationType()));
        }

        if (filter.getRecordStatus() != null) {
            builder.must(QueryBuilders.matchQuery("record_status_cd", filter.getRecordStatus()));
        }

        if (filter.getDateOfBirth() != null) {
            String dobOperator = filter.getDateOfBirthOperator();
            String dobString = (String) instantConverter.write(filter.getDateOfBirth());
            if (dobOperator == null || dobOperator.equalsIgnoreCase("equal")) {
                builder.must(QueryBuilders.matchQuery("birth_time", dobString));
            } else if (dobOperator.equalsIgnoreCase("before")) {
                builder.must(QueryBuilders.rangeQuery("birth_time").lt(dobString));
            } else if (dobOperator.equalsIgnoreCase("after")) {
                builder.must(QueryBuilders.rangeQuery("birth_time").gt(dobString));
            }
        }

        var query = new NativeSearchQueryBuilder().withQuery(builder).withSort(pageable.getSort())
                .withPageable(pageable).build();
        SearchHits<ElasticsearchPerson> elasticsearchPersonSearchHits = operations.search(query,
                ElasticsearchPerson.class);

        ids = elasticsearchPersonSearchHits
                .stream()
                .map(h -> h.getContent())
                .filter(Objects::nonNull)
                .map(ElasticsearchPerson::getPersonUid)
                .collect(Collectors.toList());
        totalCount = elasticsearchPersonSearchHits.getTotalHits();
        var persons = personRepository.findAllById(ids);
        return new PageImpl<Person>(persons, pageable, totalCount);

    }

    public Page<Person> findPatientsByOrganizationFilter(OrganizationFilter filter, GraphQLPage page) {
        // limit page size
        var pageable = GraphQLPage.toPageable(page, MAX_PAGE_SIZE);

        var organization = QOrganization.organization;
        var person = QPerson.person;
        var labEvent = QLabEvent.labEvent;
        var query = new BlazeJPAQuery<Person>(entityManager, criteriaBuilderFactory)
                .select(person)
                .from(person)
                .join(labEvent).on(labEvent.personUid.eq(person.id))
                .join(organization).on(labEvent.organizationUid.eq(organization.id));

        query = addParameter(query, organization.id::eq, filter.getId());
        query = addParameter(query, organization.displayNm::likeIgnoreCase, filter.getDisplayNm());
        query = addParameter(query, organization.streetAddr1::likeIgnoreCase, filter.getStreetAddr1());
        query = addParameter(query, organization.streetAddr2::likeIgnoreCase, filter.getStreetAddr2());
        query = addParameter(query, organization.cityDescTxt::likeIgnoreCase, filter.getCityDescTxt());
        query = addParameter(query, organization.cityCd::eq, filter.getCityCd());
        query = addParameter(query, organization.stateCd::eq, filter.getStateCd());
        query = addParameter(query, organization.zipCd::eq, filter.getZipCd());
        applySort(query, pageable.getSort());
        var results = query.fetchPage((int) pageable.getOffset(), pageable.getPageSize());
        return new PageImpl<Person>(results, pageable, results.getMaxResults());
    }

    public Page<Person> findPatientsByEvent(EventFilter filter, GraphQLPage page) {
        var pageable = GraphQLPage.toPageable(page, MAX_PAGE_SIZE);
        List<Long> ids;
        long totalCount = 0L;
        switch (filter.getEventType()) {
            case INVESTIGATION:
                var investigations = eventService.findInvestigationsByFilter(filter.getInvestigationFilter());
                ids = investigations
                        .stream()
                        .map(h -> h.getContent())
                        .filter(Objects::nonNull)
                        .filter(h -> h.getPersonCd() != null && h.getPersonCd().equals("PAT"))
                        .filter(h -> h.getPersonRecordStatusCd().equals(RecordStatus.ACTIVE.toString()))
                        .map(Investigation::getParentUid)
                        .distinct()
                        .collect(Collectors.toList());
                totalCount = ids.size();
                break;
            case LABORATORY_REPORT:
                var labReports = eventService.findLabReportsByFilter(filter.getLaboratoryReportFilter());
                ids = labReports
                        .stream()
                        .map(h -> h.getContent())
                        .filter(Objects::nonNull)
                        .filter(h -> h.getPersonCd() != null && h.getPersonCd().equals("PAT"))
                        .filter(h -> h.getPersonRecordStatusCd().equals(RecordStatus.ACTIVE.toString()))
                        .map(LabReport::getParentUid)
                        .distinct()
                        .collect(Collectors.toList());

                totalCount = ids.size();
                break;
            default:
                throw new QueryException("Invalid event type: " + filter.getEventType());
        }

        var results = personRepository.findByIdIn(ids, pageable);
        return new PageImpl<Person>(results.getContent(), pageable, totalCount);
    }

    // checks to see if the filter provided is null, if not add the filter to the
    // 'query.where' based on the expression supplied
    private <T, I> BlazeJPAQuery<T> addParameter(BlazeJPAQuery<T> query,
            Function<I, BooleanExpression> expression, I filter) {
        if (filter != null) {
            if (filter instanceof String s) {
                if (s.trim().length() == 0) {
                    return query;
                }
            }
            return query.where(expression.apply(filter));
        } else {
            return query;
        }
    }

    /*
     * NBS creates a prefix and suffix for Person.local_id.
     * The format consists of 3 parts:
     * 1. prefix -> 'PSN'
     * 2. value -> the seed: 10000000 + the id
     * 3. suffix -> 'GA01'
     * So id of 9999 would turn into 'PSN10009999GA01'
     */
    private String generateLocalId(Long id) {
        final Long seed = 10000000L;
        final Long nbsId = seed + id;
        return "PSN" + nbsId + "GA01";
    }

    @Transactional
    public Person createPatient(PatientInput input) {
        final long id = personRepository.getMaxId() + 1;
        var person = new Person();
        // generated / required values
        person.setId(id);
        person.setNBSEntity(new NBSEntity(id, "PSN"));
        person.setVersionCtrlNbr((short) 1);
        person.setAddTime(Instant.now());
        person.setRecordStatusCd(RecordStatus.ACTIVE);

        // person table
        if (input.getName() != null) {
            person.setLastNm(input.getName().getLastName());
            person.setFirstNm(input.getName().getFirstName());
            person.setMiddleNm(input.getName().getMiddleName());
            person.setNmSuffix(input.getName().getSuffix());
        }
        person.setSsn(input.getSsn());
        person.setBirthTime(input.getDateOfBirth());
        person.setBirthGenderCd(input.getBirthGender());
        person.setCurrSexCd(input.getCurrentGender());
        person.setDeceasedIndCd(input.getDeceased());
        person.setEthnicGroupInd(input.getEthnicity());

        // person_name
        addPersonNameEntry(person, input.getName());

        // person_race
        addPersonRaceEntry(person, input.getRace());

        // tele_locator
        var teleLocators = addTeleLocatorEntries(person, input.getPhoneNumbers(), input.getEmailAddresses());

        // postal_locator
        var postalLocators = addPostalLocatorEntries(person, input.getAddresses());

        // Save
        teleLocatorRepository.saveAll(teleLocators);
        postalLocatorRepository.saveAll(postalLocators);
        return personRepository.save(person);
    }

    /*
     * Creates a PersonRace entry and adds it to the Person object
     */
    private void addPersonRaceEntry(Person person, Race race) {
        if (race == null) {
            return;
        }
        var now = Instant.now();
        var personRace = new PersonRace();
        personRace.setId(new PersonRaceId(person.getId(), race));
        personRace.setPersonUid(person);
        personRace.setAddTime(now);
        personRace.setRaceCategoryCd(race);
        personRace.setRecordStatusCd("ACTIVE");

        if (person.getRaces() == null) {
            person.setRaces(Arrays.asList(personRace));
        } else {
            person.getRaces().add(personRace);
        }
    }

    /*
     * Creates a PersonName entry and adds it to the Person object
     */
    private void addPersonNameEntry(Person person, Name name) {
        var now = Instant.now();
        var personName = new PersonName();
        personName.setId(new PersonNameId(person.getId(), (short) 1));
        personName.setPersonUid(person);
        personName.setAddReasonCd("Add");
        personName.setAddTime(now);
        personName.setFirstNm(name.getFirstName());
        // personName.setFirstNmSndx(); TODO how to generate sndx
        personName.setLastNm(name.getLastName());
        // personName.setLastNmSndx();
        personName.setMiddleNm(name.getMiddleName());
        personName.setNmSuffix(name.getSuffix());
        personName.setNmUseCd("L"); // L = legal, AL = alias. per NEDSSConstants
        personName.setRecordStatusCd("ACTIVE");
        personName.setRecordStatusTime(now);
        personName.setStatusCd('A');
        personName.setStatusTime(now);

        if (person.getNames() == null) {
            person.setNames(Arrays.asList(personName));
        } else {
            person.getNames().add(personName);
        }
    }

    /**
     * Creates an EntityLocatorParticipation and a PostalLocator object for each
     * address. PostalLocators are added to the EntityLocatorParticipation which is
     * then added to the Person.NBSEntity
     */
    private List<PostalLocator> addPostalLocatorEntries(Person person, List<PostalAddress> addresses) {
        var postalLocators = new ArrayList<PostalLocator>();
        if (addresses.size() > 0) {
            var auth = SecurityContextHolder.getContext().getAuthentication();
            var user = (NbsUserDetails) auth.getPrincipal();
            // Grab highest Id from DB -- eventually fix db to auto increment
            var postalLocatorId = postalLocatorRepository.getMaxId() + 1;
            var now = Instant.now();
            var elpList = new ArrayList<EntityLocatorParticipation>();
            for (PostalAddress address : addresses) {
                var plId = postalLocatorId++;
                // entity locator participation ties person to locator entry
                var elp = new EntityLocatorParticipation();
                elp.setId(new EntityLocatorParticipationId(person.getId(), plId));
                elp.setNbsEntity(person.getNBSEntity());
                elp.setCd("H");
                elp.setClassCd("PST");
                elp.setLastChgTime(now);
                elp.setLastChgUserId(user.getId());
                elp.setRecordStatusCd("ACTIVE");
                elp.setRecordStatusTime(now);
                elp.setStatusCd('A');
                elp.setStatusTime(now);
                elp.setUseCd("H");
                elp.setVersionCtrlNbr((short) 1);

                var locator = new PostalLocator();
                locator.setId(plId);
                locator.setAddTime(now);
                locator.setCityDescTxt(address.getCity());
                locator.setCntryCd(address.getCountryCode());
                locator.setCntyCd(address.getCountyCode());
                locator.setStateCd(address.getStateCode());
                locator.setStreetAddr1(address.getStreetAddress1());
                locator.setStreetAddr2(address.getStreetAddress2());
                locator.setZipCd(address.getZip());
                locator.setCensusTract(address.getCensusTract());
                locator.setRecordStatusCd("ACTIVE");
                locator.setRecordStatusTime(now);

                elp.setLocator(locator);
                postalLocators.add(locator);
                elpList.add(elp);
            }
            // Add generated ELPs to Person.NBSEntity
            if (person.getNBSEntity().getEntityLocatorParticipations() == null) {
                person.getNBSEntity().setEntityLocatorParticipations(elpList);
            } else {
                person.getNBSEntity().getEntityLocatorParticipations().addAll(elpList);
            }
        }
        return postalLocators;
    }

    /*
     * Creates an EntityLocatorParticipation and a TeleLocator object for each phone
     * number and email address. TeleLocators are added to the
     * EntityLocatorParticipation, which is then added to the Person NBSEntity
     */
    private List<TeleLocator> addTeleLocatorEntries(Person person, List<PhoneNumber> phoneNumbers,
            List<String> emailAddresses) {
        var locatorList = new ArrayList<TeleLocator>();
        if (phoneNumbers.size() > 0 || emailAddresses.size() > 0) {
            var auth = SecurityContextHolder.getContext().getAuthentication();
            var user = (NbsUserDetails) auth.getPrincipal();
            // Grab highest Id from DB -- eventually fix db to auto increment
            var teleLocatorId = teleLocatorRepository.getMaxId() + 1;
            var now = Instant.now();
            var elpList = new ArrayList<EntityLocatorParticipation>();
            for (PhoneNumber pn : phoneNumbers) {
                var teleId = teleLocatorId++;
                // entity locator participation ties person to locator entry
                var elp = new EntityLocatorParticipation();
                elp.setId(new EntityLocatorParticipationId(person.getId(), teleId));
                elp.setNbsEntity(person.getNBSEntity());
                elp.setClassCd("TELE");
                setElpTypeFields(elp, pn.getPhoneType());
                elp.setLastChgTime(now);
                elp.setLastChgUserId(user.getId());
                elp.setRecordStatusCd("ACTIVE");
                elp.setRecordStatusTime(now);
                elp.setStatusCd('A');
                elp.setStatusTime(now);
                elp.setVersionCtrlNbr((short) 1);
                var locator = new TeleLocator();
                locator.setId(teleId);
                locator.setAddTime(now);
                locator.setAddUserId(user.getId());
                locator.setExtensionTxt(pn.getExtension());
                locator.setPhoneNbrTxt(pn.getPhoneNumber());
                locator.setRecordStatusCd("ACTIVE");

                elp.setLocator(locator);
                locatorList.add(locator);
                elpList.add(elp);
            }

            for (String email : emailAddresses) {
                var teleId = teleLocatorId++;
                // entity locator participation ties person to locator entry
                var elp = new EntityLocatorParticipation();
                elp.setId(new EntityLocatorParticipationId(person.getId(), teleId));
                elp.setNbsEntity(person.getNBSEntity());
                elp.setClassCd("TELE");
                elp.setCd("NET");
                elp.setUseCd("H");
                elp.setLastChgTime(now);
                elp.setLastChgUserId(user.getId());
                elp.setRecordStatusCd("ACTIVE");
                elp.setRecordStatusTime(now);
                elp.setStatusCd('A');
                elp.setVersionCtrlNbr((short) 1);
                var locator = new TeleLocator();
                locator.setId(teleId);
                locator.setAddTime(now);
                locator.setAddUserId(user.getId());
                locator.setEmailAddress(email);
                locator.setRecordStatusCd("ACTIVE");

                elp.setLocator(locator);
                locatorList.add(locator);
                elpList.add(elp);
            }

            // Add generated ELPs to Person.NBSEntity
            var existingElp = person.getNBSEntity().getEntityLocatorParticipations();
            if (existingElp != null && existingElp.size() > 0) {
                elpList.addAll(existingElp);
            }
            person.getNBSEntity().setEntityLocatorParticipations(elpList);
        }
        return locatorList;
    }

    private void setElpTypeFields(EntityLocatorParticipation elp, PhoneType phoneType) {
        switch (phoneType) {
            case CELL:
                elp.setCd("CP");
                elp.setUseCd("MC");
                break;
            case HOME:
                elp.setCd("PH");
                elp.setUseCd("H");
                break;
            case WORK:
                elp.setCd("PH");
                elp.setUseCd("WP");
                break;
            default:
                throw new QueryException("Invalid PhoneType specified: " + phoneType);
        }
    }

    private String addWildcards(String searchString) {
        return "*" + searchString + "*";
    }
}