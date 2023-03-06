package gov.cdc.nbs.service;

import static gov.cdc.nbs.config.security.SecurityUtil.BusinessObjects.PATIENT;
import static gov.cdc.nbs.config.security.SecurityUtil.Operations.FINDINACTIVE;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Function;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.commons.codec.language.Soundex;
import org.apache.lucene.search.join.ScoreMode;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.Operator;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.sort.SortBuilder;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import com.blazebit.persistence.CriteriaBuilderFactory;
import com.blazebit.persistence.querydsl.BlazeJPAQuery;
import com.querydsl.core.types.dsl.BooleanExpression;
import gov.cdc.nbs.config.security.NbsUserDetails;
import gov.cdc.nbs.config.security.SecurityUtil;
import gov.cdc.nbs.entity.elasticsearch.ElasticsearchPerson;
import gov.cdc.nbs.message.enums.RecordStatus;
import gov.cdc.nbs.entity.enums.converter.InstantConverter;
import gov.cdc.nbs.entity.odse.Person;
import gov.cdc.nbs.entity.odse.QLabEvent;
import gov.cdc.nbs.entity.odse.QOrganization;
import gov.cdc.nbs.entity.odse.QPerson;
import gov.cdc.nbs.exception.QueryException;
import gov.cdc.nbs.graphql.GraphQLPage;
import gov.cdc.nbs.graphql.filter.OrganizationFilter;
import gov.cdc.nbs.graphql.filter.PatientFilter;
import gov.cdc.nbs.message.PatientCreateRequest;
import gov.cdc.nbs.message.PatientDeleteRequest;
import gov.cdc.nbs.message.PatientInput;
import gov.cdc.nbs.message.PatientUpdateParams;
import gov.cdc.nbs.message.PatientUpdateRequest;
import gov.cdc.nbs.message.TemplateInput;
import gov.cdc.nbs.model.PatientCreateResponse;
import gov.cdc.nbs.model.PatientDeleteResponse;
import gov.cdc.nbs.model.PatientUpdateResponse;
import gov.cdc.nbs.patient.create.PatientCreateRequestResolver;
import gov.cdc.nbs.repository.PersonRepository;
import gov.cdc.nbs.service.util.Constants;
import graphql.com.google.common.collect.Ordering;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PatientService {

    @Value("${nbs.max-page-size: 50}")
    private Integer maxPageSize;

    @PersistenceContext
    private final EntityManager entityManager;
    private final PersonRepository personRepository;
    private final CriteriaBuilderFactory criteriaBuilderFactory;
    private final ElasticsearchOperations operations;
    private final InstantConverter instantConverter = new InstantConverter();
    private final KafkaRequestProducerService producer;
    private final PatientCreateRequestResolver createRequestResolver;
    private final UserService userService;

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
        }).toList();
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
        var pageable = GraphQLPage.toPageable(page, maxPageSize);
        var query = new BlazeJPAQuery<Person>(entityManager, criteriaBuilderFactory)
                .select(person)
                .from(person);
        query = applySort(query, pageable.getSort());

        var results = query.fetchPage((int) pageable.getOffset(),
                pageable.getPageSize());
        return new PageImpl<>(results, pageable, results.getMaxResults());
    }

    @SuppressWarnings("squid:S3776")
    // ignore high cognitive complexity as the method is simply going through the
    // passed in parameters, checking if null, and appending to the query
    public Page<Person> findPatientsByFilter(PatientFilter filter, GraphQLPage page) {
        var pageable = GraphQLPage.toPageable(page, maxPageSize);
        List<Long> ids;
        BoolQueryBuilder builder = QueryBuilders.boolQuery();

        builder.must(QueryBuilders.matchQuery(ElasticsearchPerson.CD_FIELD, "PAT"));

        if (filter.getId() != null) {
            builder.must(QueryBuilders.matchQuery(ElasticsearchPerson.LOCAL_ID, filter.getId()));
        }

        if (filter.getFirstName() != null && !filter.getFirstName().isEmpty()) {
            BoolQueryBuilder firstNameBuilder = QueryBuilders.boolQuery();
            firstNameBuilder.should(QueryBuilders.nestedQuery(ElasticsearchPerson.NAME_FIELD,
                    QueryBuilders.queryStringQuery(
                                    addWildcards(filter.getFirstName()))
                            .defaultField("name.firstNm")
                            .defaultOperator(Operator.AND),
                    ScoreMode.Avg));

            Soundex soundex = new Soundex();
            String firstNmSndx = soundex.encode(filter.getFirstName());
            firstNameBuilder.should(QueryBuilders.nestedQuery(ElasticsearchPerson.NAME_FIELD,
                    QueryBuilders.queryStringQuery(firstNmSndx).defaultField("name.firstNmSndx"),
                    ScoreMode.Avg));

            builder.must(firstNameBuilder);
        }

        if (filter.getLastName() != null && !filter.getLastName().isEmpty()) {
            BoolQueryBuilder lastNameBuilder = QueryBuilders.boolQuery();
            lastNameBuilder.should(QueryBuilders.nestedQuery(ElasticsearchPerson.NAME_FIELD,
                    QueryBuilders.queryStringQuery(
                                    addWildcards(filter.getLastName()))
                            .defaultField("name.lastNm")
                            .defaultOperator(Operator.AND),
                    ScoreMode.Avg));

            Soundex soundex = new Soundex();
            String lastNmSndx = soundex.encode(filter.getLastName());
            lastNameBuilder.should(QueryBuilders.nestedQuery(ElasticsearchPerson.NAME_FIELD,
                    QueryBuilders.queryStringQuery(lastNmSndx).defaultField("name.lastNmSndx"),
                    ScoreMode.Avg));

            builder.must(lastNameBuilder);
        }

        if (filter.getSsn() != null && !filter.getSsn().isEmpty()) {
            builder.must(QueryBuilders.matchQuery(ElasticsearchPerson.SSN_FIELD, filter.getSsn()));
        }

        if (filter.getPhoneNumber() != null && !filter.getPhoneNumber().isEmpty()) {
            builder.must(QueryBuilders.nestedQuery(ElasticsearchPerson.PHONE_FIELD,
                    QueryBuilders.queryStringQuery(filter.getPhoneNumber())
                            .defaultField("phone.telephoneNbr")
                            .defaultOperator(Operator.AND),
                    ScoreMode.Avg));
        }

        if (filter.getEmail() != null && !filter.getEmail().isEmpty()) {
            builder.must(QueryBuilders.nestedQuery(ElasticsearchPerson.EMAIL_FIELD,
                    QueryBuilders.queryStringQuery(filter.getEmail())
                            .defaultField("email.emailAddress")
                            .defaultOperator(Operator.AND),
                    ScoreMode.Avg));
        }

        if (filter.getAddress() != null && !filter.getAddress().isEmpty()) {
            builder.must(QueryBuilders.nestedQuery(ElasticsearchPerson.ADDRESS_FIELD, QueryBuilders
                            .queryStringQuery(
                                    addWildcards(filter.getAddress()))
                            .defaultField("address.streetAddr1")
                            .defaultOperator(Operator.AND),
                    ScoreMode.Avg));
        }

        if (filter.getGender() != null) {
            builder.must(QueryBuilders.matchQuery(ElasticsearchPerson.CURR_SEX_CD, filter.getGender()));
        }

        if (filter.getDeceased() != null) {
            builder.must(QueryBuilders.matchQuery(ElasticsearchPerson.DECEASED_IND_CD, filter.getDeceased()));
        }

        if (filter.getCity() != null && !filter.getCity().isEmpty()) {
            builder.must(QueryBuilders.nestedQuery(ElasticsearchPerson.ADDRESS_FIELD,
                    QueryBuilders.queryStringQuery(
                                    addWildcards(filter.getCity()))
                            .defaultField("address.city")
                            .defaultOperator(Operator.AND),
                    ScoreMode.Avg));
        }

        if (filter.getZip() != null && !filter.getZip().isEmpty()) {
            builder.must(QueryBuilders.nestedQuery(ElasticsearchPerson.ADDRESS_FIELD,
                    QueryBuilders.matchQuery("address.zip", filter.getZip()),
                    ScoreMode.Avg));
        }

        if (filter.getState() != null && !filter.getState().isEmpty()) {
            builder.must(QueryBuilders.nestedQuery(ElasticsearchPerson.ADDRESS_FIELD,
                    QueryBuilders.matchQuery("address.state", filter.getState()), ScoreMode.Avg));
        }

        if (filter.getCountry() != null && !filter.getCountry().isEmpty()) {
            builder.must(QueryBuilders.nestedQuery(ElasticsearchPerson.ADDRESS_FIELD,
                    QueryBuilders.matchQuery("address.cntryCd", filter.getCountry()), ScoreMode.Avg));
        }

        if (filter.getEthnicity() != null) {
            builder.must(QueryBuilders.matchQuery(ElasticsearchPerson.ETHNIC_GROUP_IND,
                    filter.getEthnicity()));
        }

        if (filter.getRace() != null) {
            builder.must(QueryBuilders.nestedQuery(ElasticsearchPerson.RACE_FIELD,
                    QueryBuilders.matchQuery("race.raceDescTxt", filter.getRace()), ScoreMode.Avg));
        }

        if (filter.getIdentification() != null) {
            builder.must(
                    QueryBuilders.matchQuery("identification", filter.getIdentification().getIdentificationType()));
        }

        addRecordStatusQuery(filter.getRecordStatus(), builder);

        if (filter.getDateOfBirth() != null) {
            String dobOperator = filter.getDateOfBirthOperator();
            String dobString = (String) instantConverter.write(filter.getDateOfBirth());
            if (dobOperator == null || dobOperator.equalsIgnoreCase("equal")) {
                builder.must(QueryBuilders.matchQuery(ElasticsearchPerson.BIRTH_TIME, dobString));
            } else if (dobOperator.equalsIgnoreCase("before")) {
                builder.must(QueryBuilders.rangeQuery(ElasticsearchPerson.BIRTH_TIME).lt(dobString));
            } else if (dobOperator.equalsIgnoreCase("after")) {
                builder.must(QueryBuilders.rangeQuery(ElasticsearchPerson.BIRTH_TIME).gt(dobString));
            }
        }

        var query = new NativeSearchQueryBuilder()
                .withQuery(builder)
                .withSorts(buildPatientSort(pageable))
                .withPageable(PageRequest.of(pageable.getPageNumber(), pageable.getPageSize()))
                .build();

        SearchHits<ElasticsearchPerson> elasticsearchPersonSearchHits = operations.search(query,
                ElasticsearchPerson.class);

        ids = elasticsearchPersonSearchHits
                .stream()
                .map(h -> h.getContent())
                .filter(Objects::nonNull)
                .map(ElasticsearchPerson::getPersonUid)
                .toList();
        var persons = personRepository.findAllById(ids);
        persons.sort(Ordering.explicit(ids).onResultOf(Person::getId));
        return new PageImpl<>(persons, pageable, elasticsearchPersonSearchHits.getTotalHits());
    }

    public Page<Person> findPatientsByOrganizationFilter(OrganizationFilter filter, GraphQLPage page) {
        // limit page size
        var pageable = GraphQLPage.toPageable(page, maxPageSize);

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
        return new PageImpl<>(results, pageable, results.getMaxResults());
    }

    // checks to see if the filter provided is null, if not add the filter to the
    // 'query.where' based on the expression supplied
    private <T, I> BlazeJPAQuery<T> addParameter(BlazeJPAQuery<T> query,
            Function<I, BooleanExpression> expression, I filter) {
        if (filter != null) {
            if (filter instanceof String s && s.trim().length() == 0) {
                return query;
            }
            return query.where(expression.apply(filter));
        } else {
            return query;
        }
    }

    /**
     * Adds the record status to the query builder. If no record status is specified, throw a QueryException.
     */
    private void addRecordStatusQuery(Collection<RecordStatus> recordStatus, BoolQueryBuilder builder) {
        if (recordStatus == null || recordStatus.isEmpty()) {
            throw new QueryException("At least one RecordStatus is required");
        }
        // If LOG_DEL or SUPERCEDED are specified, user must have FINDINACTIVE-PATIENT
        // authority
        if (recordStatus.contains(RecordStatus.SUPERCEDED) || recordStatus.contains(RecordStatus.LOG_DEL)) {
            var currentUser = SecurityUtil.getUserDetails();
            // If user lacks permission, remove these from the search criteria
            if (!userService.isAuthorized(currentUser, FINDINACTIVE + "-" + PATIENT)) {
                recordStatus = recordStatus.stream()
                        .filter(s -> !s.equals(RecordStatus.SUPERCEDED) && !s.equals(RecordStatus.LOG_DEL))
                        .toList();
            }

        }

        if (recordStatus.isEmpty()) {
            // User selected either SUPERCEDED or LOG_DEL and lacks the permission.
            throw new QueryException("User does not have permission to search by the specified RecordStatus");
        }
        var recordStatusStrings = recordStatus.stream().map(RecordStatus::toString).toList();
        builder.must(QueryBuilders.termsQuery(ElasticsearchPerson.RECORD_STATUS_CD, recordStatusStrings));
    }

    /**
     * Generates a patient id and localId. Posts the request along with Id's to Kafka
     */
    public PatientCreateResponse sendCreatePatientRequest(PatientInput input) {
        // create 'create patient' message and post to kafka
        var requestId = getRequestID();
        var user = (NbsUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        PatientCreateRequest createRequest = this.createRequestResolver.create(user.getId(), requestId, input);
        producer.requestPatientCreateEnvelope(createRequest);
        return new PatientCreateResponse(
                createRequest.request(),
                createRequest.patient());
    }
    
	/**
	 * Send updated Person Event to kakfa topic to be picked up and updated.
	 * 
	 * @param id
	 * @param input
	 * @return
	 */
	public PatientUpdateResponse sendUpdatePatientEvent(Long id, PatientInput input) {
		String requestId = null;

		if (input != null) {
			List<TemplateInput> templateInputs = new ArrayList<>();

			PatientUpdateParams patientUpdatedPayLoad = PatientUpdateParams.builder().input(input).personId(id)
					.templateInputs(templateInputs).build();

			requestId = getRequestID();

			var patientUpdateRequest = new PatientUpdateRequest(requestId, patientUpdatedPayLoad);
			producer.requestPatientUpdateEnvelope(patientUpdateRequest);
		}

		return PatientUpdateResponse.builder().requestId(requestId).build();

	}

    public PatientDeleteResponse sendDeletePatientEvent(Long id, PatientInput input) {
        String requestId = getRequestID();
        var patientDeleteRequest = new PatientDeleteRequest(requestId);
        producer.requestPatientDeleteEnvelope(patientDeleteRequest);

        return PatientDeleteResponse.builder().requestId(requestId).build();

    }

    private String addWildcards(String searchString) {
        // wildcard does not default to case insensitive searching
        return searchString.toLowerCase().trim() + "*";
    }

    private Collection<SortBuilder<?>> buildPatientSort(Pageable pageable) {
        if (pageable.getSort().isEmpty()) {
            return new ArrayList<>();
        }
        Collection<SortBuilder<?>> sorts = new ArrayList<>();
        pageable.getSort().stream().forEach(sort -> {
            switch (sort.getProperty()) {
                case "lastNm":
                    sorts.add(SortBuilders.fieldSort(ElasticsearchPerson.LAST_NM_KEYWORD)
                            .order(sort.getDirection() == Direction.DESC ? SortOrder.DESC : SortOrder.ASC));
                    break;
                case "birthTime":
                    sorts.add(SortBuilders.fieldSort(ElasticsearchPerson.BIRTH_TIME)
                            .order(sort.getDirection() == Direction.DESC ? SortOrder.DESC : SortOrder.ASC));
                    break;
                default:
                    throw new IllegalArgumentException("Invalid sort operator specified: " + sort.getProperty());
            }
        });
        return sorts;
    }

    private String getRequestID() {
        return String.format(Constants.APP_ID + "_%s", UUID.randomUUID());
    }
}
