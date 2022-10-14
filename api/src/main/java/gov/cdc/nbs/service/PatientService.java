package gov.cdc.nbs.service;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;

import gov.cdc.nbs.entity.odse.Act;
import gov.cdc.nbs.entity.odse.NBSEntity;
import gov.cdc.nbs.entity.odse.Organization;
import gov.cdc.nbs.entity.odse.Person;
import gov.cdc.nbs.entity.odse.QEntityId;
import gov.cdc.nbs.entity.odse.QEntityLocatorParticipation;
import gov.cdc.nbs.entity.odse.QLabEvent;
import gov.cdc.nbs.entity.odse.QParticipation;
import gov.cdc.nbs.entity.odse.QPerson;
import gov.cdc.nbs.entity.odse.QPersonEthnicGroup;
import gov.cdc.nbs.entity.odse.QPersonName;
import gov.cdc.nbs.entity.odse.QPersonRace;
import gov.cdc.nbs.entity.odse.QPostalLocator;
import gov.cdc.nbs.entity.odse.QTeleLocator;
import gov.cdc.nbs.entity.srte.QCountryCode;
import gov.cdc.nbs.entity.srte.QStateCode;
import gov.cdc.nbs.exception.QueryException;
import gov.cdc.nbs.graphql.GraphQLPage;
import gov.cdc.nbs.graphql.input.PatientInput;
import gov.cdc.nbs.graphql.searchFilter.EventFilter;
import gov.cdc.nbs.graphql.searchFilter.OrganizationFilter;
import gov.cdc.nbs.graphql.searchFilter.PatientFilter;
import gov.cdc.nbs.repository.PersonRepository;
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
    private final EventService eventService;

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
        var personEthnicGroup = QPersonEthnicGroup.personEthnicGroup;
        var entityId = QEntityId.entityId;
        var entityLocatorParticipation = QEntityLocatorParticipation.entityLocatorParticipation;
        var postalLocator = QPostalLocator.postalLocator;
        var teleLocator = QTeleLocator.teleLocator;
        var stateCode = QStateCode.stateCode;
        var countryCode = QCountryCode.countryCode;
        // physical locator?
        var query = queryFactory.selectFrom(person)
                .join(personName)
                .on(personName.id.personUid.eq(person.id))
                .join(personRace)
                .on(personRace.id.personUid.eq(person.id))
                .join(personEthnicGroup)
                .on(personEthnicGroup.id.personUid.eq(person.id))
                .join(entityId)
                .on(entityId.NBSEntityUid.eq(person.NBSEntity))
                .join(entityLocatorParticipation)
                .on(entityLocatorParticipation.entityUid.eq(person.NBSEntity))
                .join(postalLocator)
                .on(postalLocator.id.eq(entityLocatorParticipation.id.locatorUid))
                .join(stateCode)
                .on(stateCode.id.eq(postalLocator.stateCd))
                .join(countryCode)
                .on(countryCode.id.eq(postalLocator.cntryCd))
                .join(teleLocator)
                .on(teleLocator.id.eq(entityLocatorParticipation.id.locatorUid));

        // person_name -- done
        // person_race
        // person_ethnic_group
        // entity_id - identifications
        // entity_locator_participation -> postal_locator / tele_locator -- done
        // Person Id
        query = addParameter(query, person.id::eq, filter.getId());
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
        // Phone Number
        if (filter.getPhoneNumber() != null) {
            query = query.where(teleLocator.phoneNbrTxt.eq(filter.getPhoneNumber()));
        }
        // DOB
        query = query
                .where(getDateOfBirthExpression(person, filter.getDateOfBirth(), filter.getDateOfBirthOperator()));
        // Gender
        query = addParameter(query, person.birthGenderCd::eq, filter.getGender());
        // Deceased
        query = addParameter(query, person.deceasedIndCd::eq, filter.getDeceased());
        // Street Address
        query = addParameter(query,
                (x) -> postalLocator.streetAddr1.eq(x).or(postalLocator.streetAddr2.eq(x)),
                filter.getAddress());
        // City
        query = addParameter(query,
                (x) -> postalLocator.cityCd.eq(x).or(postalLocator.cityDescTxt.eq(x)),
                filter.getCity());
        // Zip
        query = addParameter(query, postalLocator.zipCd::eq, filter.getZip());
        // State
        query = addParameter(query, stateCode.codeDescTxt::equalsIgnoreCase, filter.getState());
        // Country
        query = addParameter(query, countryCode.codeDescTxt::eq, filter.getCountry());
        // Ethnicity
        query = addParameter(query, personEthnicGroup.id.ethnicGroupCd::eq, filter.getEthnicity());
        query = addParameter(query, person.recordStatusCd::eq, filter.getRecordStatus());
        return query.limit(pageable.getPageSize())
                .offset(pageable.getOffset()).fetch();

    }

    /**
     * TODO does not populate related tables (e.g. person_name, person_race,
     * person_ethnic_group, etc)
     *
     * @param patient
     * @return
     */
    public Person createPatient(PatientInput patient) {
        final long id = personRepository.getMaxId() + 1;
        var person = new Person();
        // provided values
        person.setLastNm(patient.getLastName());
        person.setFirstNm(patient.getFirstName());
        person.setSsn(patient.getSsn());
        person.setHmPhoneNbr(patient.getPhoneNumber());
        person.setBirthTime(patient.getDateOfBirth());
        person.setBirthGenderCd(patient.getGender());
        person.setDeceasedIndCd(patient.getDeceased());
        person.setHmStreetAddr1(patient.getAddress());
        person.setHmCityCd(patient.getCity());
        person.setHmStateCd(patient.getState());
        person.setHmCntryCd(patient.getCountry());
        person.setHmZipCd(patient.getZip());
        person.setEthnicityGroupCd(patient.getEthnicity());
        // generated / required values
        person.setId(id);
        person.setNBSEntity(new NBSEntity(id, "PSN"));
        person.setVersionCtrlNbr((short) 1);
        person.setAddTime(Instant.now());
        return personRepository.save(person);
    }

    public boolean deletePatient(Long id) {
        if (personRepository.findById(id).isEmpty()) {
            throw new QueryException("No patient found with id: " + id);
        }
        personRepository.deleteById(id);
        return true;
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
                acts = eventService.findInvestigationsByFilter(filter.getInvestigationFilter(), pageable);
                break;
            case LABORATORY_REPORT:
                acts = eventService.findLabReportsByFilter(filter.getLaboratoryReportFilter(), pageable);
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
                    .where(person.cd.eq("PAT")).fetch();
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