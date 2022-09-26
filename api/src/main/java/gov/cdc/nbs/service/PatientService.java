package gov.cdc.nbs.service;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import gov.cdc.nbs.entity.Person;
import gov.cdc.nbs.entity.QPerson;
import com.querydsl.jpa.impl.JPAQuery;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import gov.cdc.nbs.graphql.GraphQLPage;
import gov.cdc.nbs.graphql.PatientFilter;
import gov.cdc.nbs.repository.PersonRepository;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class PatientService {
    private final int MAX_PAGE_SIZE = 50;

    @PersistenceContext
    private final EntityManager entityManager;
    private final PersonRepository personRepository;

    public Optional<Person> findPatientById(Long id) {
        return personRepository.findById(id);
    }

    public Page<Person> findAllPatients(GraphQLPage page) {
        if (page == null) {
            page = new GraphQLPage(MAX_PAGE_SIZE, 0);
        }
        var pageable = PageRequest.of(page.getPageNumber(), Math.min(page.getPageSize(), MAX_PAGE_SIZE));
        return personRepository.findAll(pageable);
    }

    public List<Person> findPatientsByFilter(PatientFilter filter) {
        // limit page size
        if (filter.getPage().getPageSize() == 0 || filter.getPage().getPageSize() > MAX_PAGE_SIZE) {
            filter.getPage().setPageSize(MAX_PAGE_SIZE);
        }

        JPAQueryFactory queryFactory = new JPAQueryFactory(entityManager);

        var person = QPerson.person;
        var query = queryFactory.selectFrom(person);
        query = applyIfFilterNotNull(query, person.id::eq, filter.getId());
        query = applyIfFilterNotNull(query, person.lastNm::likeIgnoreCase, filter.getLastName());
        query = applyIfFilterNotNull(query, person.firstNm::likeIgnoreCase, filter.getFirstName());
        query = applyIfFilterNotNull(query, person.ssn::eq, filter.getSsn());
        if (filter.getPhoneNumber() != null) {
            query = query.where(
                    person.hmPhoneNbr.eq(filter.getPhoneNumber())
                            .or(person.wkPhoneNbr.eq(filter.getPhoneNumber()))
                            .or(person.cellPhoneNbr.eq(filter.getPhoneNumber())));
        }
        query = query
                .where(getDateOfBirthExpression(person, filter.getDateOfBirth(), filter.getDateOfBirthOperator()));
        query = applyIfFilterNotNull(query, person.birthGenderCd::eq, filter.getGender());
        query = applyIfFilterNotNull(query, person.deceasedIndCd::eq, filter.getDeceasedDataValue());
        query = applyIfFilterNotNull(query, person.hmStreetAddr1::eq, filter.getAddress());
        query = applyIfFilterNotNull(query, person.hmCityCd::eq, filter.getCity());
        query = applyIfFilterNotNull(query, person.hmStateCd::eq, filter.getState());
        query = applyIfFilterNotNull(query, person.hmCntryCd::eq, filter.getCountry());
        query = applyIfFilterNotNull(query, person.ethnicityGroupCd::eq, filter.getEthnicity());
        query = applyIfFilterNotNull(query, person.recordStatusCd::eq, filter.getRecordStatus());
        return query.limit(filter.getPage().getPageSize())
                .offset(filter.getPage().getOffset()).fetch();

    }

    private <T> JPAQuery<Person> applyIfFilterNotNull(JPAQuery<Person> query,
                                                      Function<T, BooleanExpression> expression, T parameter) {
        if (parameter != null) {
            return query.where(expression.apply(parameter));
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
            throw new IllegalArgumentException("Invalid value for Date of Birth operator");
        }
    }

}
