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
import gov.cdc.nbs.entity.odse.QLabEvent;
import gov.cdc.nbs.entity.odse.QParticipation;
import gov.cdc.nbs.entity.odse.QPerson;
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
        var query = queryFactory.selectFrom(person);
        query = addParameter(query, person.id::eq, filter.getId());
        query = addParameter(query,
                (p) -> person.lastNm.likeIgnoreCase(p, '!'),
                generateLikeString(filter.getLastName()));
        query = addParameter(query,
                (p) -> person.firstNm.likeIgnoreCase(p, '!'),
                generateLikeString(filter.getFirstName()));
        query = addParameter(query, person.ssn::eq, filter.getSsn());
        if (filter.getPhoneNumber() != null) {
            query = query.where(
                    person.hmPhoneNbr.eq(filter.getPhoneNumber())
                            .or(person.wkPhoneNbr.eq(filter.getPhoneNumber()))
                            .or(person.cellPhoneNbr.eq(filter.getPhoneNumber())));
        }
        query = query
                .where(getDateOfBirthExpression(person, filter.getDateOfBirth(), filter.getDateOfBirthOperator()));
        query = addParameter(query, person.birthGenderCd::eq, filter.getGender());
        query = addParameter(query, person.deceasedIndCd::eq, filter.getDeceased());
        query = addParameter(query, person.hmStreetAddr1::eq, filter.getAddress());
        query = addParameter(query, person.hmCityCd::eq, filter.getCity());
        query = addParameter(query, person.hmZipCd::contains, filter.getZip());
        query = addParameter(query, person.hmStateCd::equalsIgnoreCase, filter.getState());
        query = addParameter(query, person.hmCntryCd::eq, filter.getCountry());
        query = addParameter(query, person.ethnicityGroupCd::eq, filter.getEthnicity());
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