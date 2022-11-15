package gov.cdc.nbs.service;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;

import gov.cdc.nbs.config.security.NbsUserDetails;
import gov.cdc.nbs.config.security.SecurityUtil;
import gov.cdc.nbs.entity.enums.Race;
import gov.cdc.nbs.entity.enums.RecordStatus;
import gov.cdc.nbs.entity.odse.Act;
import gov.cdc.nbs.entity.odse.EntityLocatorParticipation;
import gov.cdc.nbs.entity.odse.EntityLocatorParticipationId;
import gov.cdc.nbs.entity.odse.NBSEntity;
import gov.cdc.nbs.entity.odse.Organization;
import gov.cdc.nbs.entity.odse.Person;
import gov.cdc.nbs.entity.odse.PersonName;
import gov.cdc.nbs.entity.odse.PersonNameId;
import gov.cdc.nbs.entity.odse.PersonRace;
import gov.cdc.nbs.entity.odse.PersonRaceId;
import gov.cdc.nbs.entity.odse.PostalLocator;
import gov.cdc.nbs.entity.odse.QEntityId;
import gov.cdc.nbs.entity.odse.QEntityLocatorParticipation;
import gov.cdc.nbs.entity.odse.QIntervention;
import gov.cdc.nbs.entity.odse.QLabEvent;
import gov.cdc.nbs.entity.odse.QParticipation;
import gov.cdc.nbs.entity.odse.QPerson;
import gov.cdc.nbs.entity.odse.QPersonName;
import gov.cdc.nbs.entity.odse.QPersonRace;
import gov.cdc.nbs.entity.odse.QPostalLocator;
import gov.cdc.nbs.entity.odse.QTeleLocator;
import gov.cdc.nbs.entity.odse.QTreatment;
import gov.cdc.nbs.entity.odse.TeleLocator;
import gov.cdc.nbs.entity.srte.QCountryCode;
import gov.cdc.nbs.entity.srte.QStateCode;
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
    private final OrganizationService organizationService;
    private final TeleLocatorRepository teleLocatorRepository;
    private final PostalLocatorRepository postalLocatorRepository;
    private final EventService eventService;
    private final SecurityService securityService;

    public Optional<Person> findPatientById(Long id) {
        return personRepository.findById(id);
    }

    public Page<Person> findAllPatients(GraphQLPage page) {
        var pageable = GraphQLPage.toPageable(page, MAX_PAGE_SIZE);
        return personRepository.findAll(pageable);
    }

    public List<Person> findPatientsByFilter(PatientFilter filter, GraphQLPage page) {
        // limit page size
        var pageable = GraphQLPage.toPageable(page, MAX_PAGE_SIZE);

        JPAQueryFactory queryFactory = new JPAQueryFactory(entityManager);

        var person = QPerson.person;
        var personName = QPersonName.personName;
        var personRace = QPersonRace.personRace;
        var entityId = QEntityId.entityId;
        var entityLocatorParticipation = QEntityLocatorParticipation.entityLocatorParticipation;
        var postalLocator = QPostalLocator.postalLocator;
        var teleLocator = QTeleLocator.teleLocator;
        var stateCode = QStateCode.stateCode;
        var countryCode = QCountryCode.countryCode;
        var participation = QParticipation.participation;
        var intervention = QIntervention.intervention;
        var treatment = QTreatment.treatment;
        var query = queryFactory.selectDistinct(person).from(person)
                .leftJoin(personName)
                .on(personName.id.personUid.eq(person.id))
                .leftJoin(personRace)
                .on(personRace.id.personUid.eq(person.id))
                .leftJoin(entityId)
                .on(entityId.NBSEntityUid.eq(person.NBSEntity))
                .leftJoin(entityLocatorParticipation)
                .on(person.NBSEntity.eq(entityLocatorParticipation.nbsEntity))
                .leftJoin(postalLocator)
                .on(entityLocatorParticipation.id.locatorUid.eq(postalLocator.id))
                .leftJoin(stateCode)
                .on(postalLocator.stateCd.eq(stateCode.id))
                .leftJoin(countryCode)
                .on(postalLocator.cntryCd.eq(countryCode.id))
                .leftJoin(teleLocator)
                .on(entityLocatorParticipation.id.locatorUid.eq(teleLocator.id))
                .leftJoin(participation)
                .on(person.id.eq(participation.id.subjectEntityUid))
                .leftJoin(intervention)
                .on(participation.actUid.id.eq(intervention.id))
                .leftJoin(treatment)
                .on(participation.actUid.id.eq(treatment.id));

        // Person Id
        query = addParameter(query, (x) -> person.id.eq(x).or(person.localId.eq(generateLocalId(x))), filter.getId());
        // Last Name
        query = addParameter(query,
                (p) -> personName.lastNm.likeIgnoreCase(p, '!'),
                generateLikeString(filter.getLastName()));
        // First Name
        query = addParameter(query,
                (p) -> personName.firstNm.likeIgnoreCase(p, '!'),
                generateLikeString(filter.getFirstName()));
        // SSN
        query = addParameter(query, person.ssn::eq, filter.getSsn());
        // Phone Number and address query combined as both are on
        // EntityLocatorParticipation
        if (filter.getPhoneNumber() != null) {
            // Street Address
            if (filter.getAddress() != null) {
                query = query.where(teleLocator.phoneNbrTxt.eq(filter.getPhoneNumber()).or(
                        postalLocator.streetAddr1.eq(filter.getAddress())
                                .or(postalLocator.streetAddr2.eq(filter.getAddress()))));
            } else {
                query = query.where(teleLocator.phoneNbrTxt.eq(filter.getPhoneNumber()));
            }
        } else if (filter.getAddress() != null) {
            // Street Address
            query = addParameter(query,
                    (x) -> postalLocator.streetAddr1.eq(x).or(postalLocator.streetAddr2.eq(x)),
                    filter.getAddress());
        }
        // DOB
        query = query
                .where(getDateOfBirthExpression(person, filter.getDateOfBirth(), filter.getDateOfBirthOperator()));
        // Gender
        query = addParameter(query, person.birthGenderCd::eq, filter.getGender());
        // Deceased
        query = addParameter(query, person.deceasedIndCd::eq, filter.getDeceased());
        // City
        query = addParameter(query,
                (x) -> postalLocator.cityCd.eq(x).or(postalLocator.cityDescTxt.eq(x)),
                filter.getCity());
        // Zip
        query = addParameter(query, postalLocator.zipCd::eq, filter.getZip());
        // State
        query = addParameter(query, stateCode.id::eq, filter.getState());
        // Country
        query = addParameter(query, countryCode.id::eq, filter.getCountry());
        // Ethnicity
        query = addParameter(query, person.ethnicGroupInd::eq, filter.getEthnicity());
        // Race
        query = addParameter(query, personRace.id.raceCd::eq, filter.getRace());
        // Identification
        query = addParameter(query,
                (x) -> entityId.typeCd.eq(x.getIdentificationType())
                        .and(entityId.rootExtensionTxt.eq(x.getIdentificationNumber())),
                filter.getIdentification());
        // Vaccination Id
        query = addParameter(query, (x) -> intervention.localId.eq(x).and(participation.id.typeCd.eq("SubOfVacc")),
                filter.getVaccinationId());
        // Treatment Id
        if (filter.getTreatmentId() != null) {
            // Treatment data is secured by Program Area
            var userDetails = SecurityUtil.getUserDetails();
            var programAreas = securityService.getProgramAreaCodes(userDetails);
            query.where(treatment.localId.eq(filter.getTreatmentId())
                    .and(participation.id.typeCd.eq("SubjOfTrmt")
                            .and(treatment.progAreaCd.in(programAreas))));
        }
        // Record status
        query = addParameter(query, person.recordStatusCd::eq, filter.getRecordStatus());

        return query.limit(pageable.getPageSize())
                .offset(pageable.getOffset()).fetch();

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

    public List<Person> findPatientsByOrganizationFilter(OrganizationFilter filter, GraphQLPage page) {
        var pageable = GraphQLPage.toPageable(page, MAX_PAGE_SIZE);

        List<Organization> organizationList = organizationService.findOrganizationsByFilter(filter, page);
        List<Long> organizationUids = new ArrayList<Long>();

        for (Organization organization : organizationList) {
            organizationUids.add(organization.getId());
        }

        JPAQueryFactory queryFactory = new JPAQueryFactory(entityManager);
        var labEvent = QLabEvent.labEvent;
        var person = QPerson.person;
        return queryFactory.selectFrom(person)
                .innerJoin(labEvent)
                .on(labEvent.personUid.eq(person.id))
                .where(labEvent.organizationUid.in(organizationUids))
                .orderBy(person.lastNm.asc())
                .orderBy(person.firstNm.asc())
                .limit(pageable.getPageSize())
                .offset(pageable.getOffset())
                .fetch();
    }

    public List<Person> findPatientsByEvent(EventFilter filter, GraphQLPage page) {
        var pageable = GraphQLPage.toPageable(page, MAX_PAGE_SIZE);
        JPAQueryFactory queryFactory = new JPAQueryFactory(entityManager);
        List<Act> acts;
        switch (filter.getEventType()) {
            case INVESTIGATION:
                // Get all Act entries matching filter
                acts = eventService.findInvestigationsByFilter(filter.getInvestigationFilter(), PageRequest.of(0, 500));
                break;
            case LABORATORY_REPORT:
                acts = eventService.findLabReportsByFilter(filter.getLaboratoryReportFilter(), PageRequest.of(0, 500));
                break;
            default:
                throw new QueryException("Invalid event type: " + filter.getEventType());
        }
        // Find patients that correspond to the Act entries
        var actIds = acts.stream().filter(a -> a.getMoodCd().equals("EVN")).map(Act::getId)
                .collect(Collectors.toList());
        if (actIds.size() > 0) {
            var person = QPerson.person;
            var participation = QParticipation.participation;
            return queryFactory.selectFrom(person)
                    .innerJoin(participation)
                    .on(person.id.eq(participation.id.subjectEntityUid))
                    .where(participation.actUid.id.in(actIds))
                    .where(person.recordStatusCd.eq(RecordStatus.ACTIVE))
                    .where(person.cd.eq("PAT"))
                    .limit(pageable.getPageSize()).offset(pageable.getOffset()).fetch();
        } else {
            return new ArrayList<>();
        }
    }

    // checks to see if the filter provided is null, if not add the filter to the
    // 'query.where' based on the expression supplied
    private <T> JPAQuery<Person> addParameter(JPAQuery<Person> query,
            Function<T, BooleanExpression> expression, T filter) {
        if (filter != null) {
            return query.where(expression.apply(filter));
        } else {
            return query;
        }
    }

    private BooleanExpression getDateOfBirthExpression(QPerson qPerson, Instant dob, String dobOperator) {
        if (dob == null) {
            return null;
        }
        if (dobOperator == null) {
            return qPerson.birthTime.eq(dob);
        } else if (dobOperator.toLowerCase().equals("equal")) {
            return qPerson.birthTime.eq(dob);
        } else if (dobOperator.toLowerCase().equals("before")) {
            return qPerson.birthTime.before(dob);
        } else if (dobOperator.toLowerCase().equals("after")) {
            return qPerson.birthTime.after(dob);
        } else {
            throw new QueryException("Invalid value for Date of Birth operator");
        }
    }

    // MSSQL requires us to escape the '[' character, this is not provided in
    // querydsl, so we do it here
    private String generateLikeString(String originalString) {
        if (originalString == null) {
            return null;
        }
        return "%" + originalString.replace("[", "![") + "%";
    }
}