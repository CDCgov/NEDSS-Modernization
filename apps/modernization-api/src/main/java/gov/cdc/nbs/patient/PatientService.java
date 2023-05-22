package gov.cdc.nbs.patient;

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

import gov.cdc.nbs.message.patient.event.PatientRequest;
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
import org.springframework.stereotype.Service;
import com.blazebit.persistence.CriteriaBuilderFactory;
import com.blazebit.persistence.querydsl.BlazeJPAQuery;
import com.querydsl.core.types.dsl.BooleanExpression;
import gov.cdc.nbs.config.security.SecurityUtil;
import gov.cdc.nbs.entity.elasticsearch.ElasticsearchPerson;
import gov.cdc.nbs.entity.enums.RecordStatus;
import gov.cdc.nbs.entity.odse.Person;
import gov.cdc.nbs.entity.odse.QLabEvent;
import gov.cdc.nbs.entity.odse.QOrganization;
import gov.cdc.nbs.entity.odse.QPerson;
import gov.cdc.nbs.exception.QueryException;
import gov.cdc.nbs.graphql.GraphQLPage;
import gov.cdc.nbs.graphql.filter.OrganizationFilter;
import gov.cdc.nbs.graphql.filter.PatientFilter;
import gov.cdc.nbs.message.patient.input.AdministrativeInput;
import gov.cdc.nbs.message.patient.input.GeneralInfoInput;
import gov.cdc.nbs.message.patient.input.MortalityInput;
import gov.cdc.nbs.message.patient.input.NameInput;
import gov.cdc.nbs.message.patient.input.PatientInput;
import gov.cdc.nbs.message.patient.input.SexAndBirthInput;
import gov.cdc.nbs.message.util.Constants;
import gov.cdc.nbs.model.PatientEventResponse;
import gov.cdc.nbs.patient.create.PatientCreateRequestResolver;
import gov.cdc.nbs.patient.identifier.PatientIdentifierSettings;
import gov.cdc.nbs.patient.identifier.PatientLocalIdentifierResolver;
import gov.cdc.nbs.patient.kafka.KafkaProducer;
import gov.cdc.nbs.repository.PersonRepository;
import gov.cdc.nbs.service.UserService;
import gov.cdc.nbs.time.FlexibleInstantConverter;
import graphql.com.google.common.collect.Ordering;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PatientService {
    private static final float FIRST_NAME_PRIMARY_BOOST = 2.0f;
    private static final float FIRST_NAME_NON_PRIMARY_BOOST = 1.0f;
    private static final float FIRST_NAME_SOUNDEX_BOOST = 0.5f;
    private static final float LAST_NAME_PRIMARY_BOOST = 2.0f;
    private static final float LAST_NAME_NON_PRIMARY_BOOST = 1.0f;
    private static final float LAST_NAME_SOUNDEX_BOOST = 0.5f;

    @Value("${nbs.max-page-size: 50}")
    private Integer maxPageSize;

    @PersistenceContext
    private final EntityManager entityManager;
    private final PersonRepository personRepository;
    private final CriteriaBuilderFactory criteriaBuilderFactory;
    private final ElasticsearchOperations operations;
    private final KafkaProducer producer;
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
            if (Character.isDigit(filter.getId().charAt(0))) {
                try {
                    long shortId = Long.parseLong(filter.getId());
                    PatientIdentifierSettings settings = new PatientIdentifierSettings(
                        // TODO get these from global settings
                        "PSN",
                        1,
                        "GA01"
                    );
                    PatientLocalIdentifierResolver resolver = new PatientLocalIdentifierResolver(settings);
                    String localId = resolver.resolve(shortId);
                    builder.must(QueryBuilders.matchQuery(ElasticsearchPerson.LOCAL_ID, localId));    
                } catch (NumberFormatException exception) {
                    // skip this criteria.  it's not a short id or long id
                }
            }
            else {
                builder.must(QueryBuilders.matchQuery(ElasticsearchPerson.LOCAL_ID, filter.getId()));
            }
        }

        if (filter.getFirstName() != null && !filter.getFirstName().isEmpty()) {
            BoolQueryBuilder firstNameBuilder = QueryBuilders.boolQuery();

            firstNameBuilder.should(QueryBuilders.matchQuery(ElasticsearchPerson.FIRST_NM,
                    filter.getFirstName().trim()).boost(FIRST_NAME_PRIMARY_BOOST));

            firstNameBuilder.should(QueryBuilders.nestedQuery(ElasticsearchPerson.NAME_FIELD,
                    QueryBuilders.queryStringQuery(
                            addWildcards(filter.getFirstName()))
                            .defaultField("name.firstNm")
                            .defaultOperator(Operator.AND),
                    ScoreMode.Avg).boost(FIRST_NAME_NON_PRIMARY_BOOST));

            Soundex soundex = new Soundex();
            String firstNmSndx = soundex.encode(filter.getFirstName().trim());
            firstNameBuilder.should(QueryBuilders.nestedQuery(ElasticsearchPerson.NAME_FIELD,
                    QueryBuilders.queryStringQuery(firstNmSndx).defaultField("name.firstNmSndx"),
                    ScoreMode.Avg).boost(FIRST_NAME_SOUNDEX_BOOST));

            builder.must(firstNameBuilder);
        }

        if (filter.getLastName() != null && !filter.getLastName().isEmpty()) {
            BoolQueryBuilder lastNameBuilder = QueryBuilders.boolQuery();

            lastNameBuilder.should(QueryBuilders.matchQuery(ElasticsearchPerson.LAST_NM_KEYWORD,
                    filter.getLastName().trim()).boost(LAST_NAME_PRIMARY_BOOST));

            lastNameBuilder.should(QueryBuilders.nestedQuery(ElasticsearchPerson.NAME_FIELD,
                    QueryBuilders.queryStringQuery(
                            addWildcards(filter.getLastName()))
                            .defaultField("name.lastNm")
                            .defaultOperator(Operator.AND),
                    ScoreMode.Avg).boost(LAST_NAME_NON_PRIMARY_BOOST));

            Soundex soundex = new Soundex();
            String lastNmSndx = soundex.encode(filter.getLastName().trim());
            lastNameBuilder.should(QueryBuilders.nestedQuery(ElasticsearchPerson.NAME_FIELD,
                    QueryBuilders.queryStringQuery(lastNmSndx).defaultField("name.lastNmSndx"),
                    ScoreMode.Avg).boost(LAST_NAME_SOUNDEX_BOOST));

            builder.must(lastNameBuilder);
        }

        if (filter.getSsn() != null && !filter.getSsn().isEmpty()) {
            String allDigitSsn = filter.getSsn().replaceAll("\\D", "");
            if (!allDigitSsn.isEmpty()) {
                builder.must(QueryBuilders.queryStringQuery(
                        addWildcards(allDigitSsn))
                        .defaultField(ElasticsearchPerson.SSN_FIELD)
                        .defaultOperator(Operator.AND));
            }
        }

        if (filter.getPhoneNumber() != null) {
            String allDigitPhoneNumber = filter.getPhoneNumber().replaceAll("\\D", "");
            if (!allDigitPhoneNumber.isEmpty()) {
                builder.must(QueryBuilders.nestedQuery(ElasticsearchPerson.PHONE_FIELD,
                        QueryBuilders.queryStringQuery(
                                addWildcards(allDigitPhoneNumber))
                                .defaultField("phone.telephoneNbr")
                                .defaultOperator(Operator.AND),
                        ScoreMode.Avg));
            }
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
                    QueryBuilders.queryStringQuery(filter.getRace())
                            .defaultField("race.raceCategoryCd")
                            .defaultOperator(Operator.AND),
                    ScoreMode.Avg));
        }

        if (filter.getIdentification() != null) {
            String allAlphanumericIdentificationNumber = filter.getIdentification().getIdentificationNumber()
                    .replaceAll("\\W", "");
            if (!allAlphanumericIdentificationNumber.isEmpty()) {
                builder.must(QueryBuilders.nestedQuery(ElasticsearchPerson.ENTITY_ID_FIELD,
                        QueryBuilders.queryStringQuery(
                                addWildcards(allAlphanumericIdentificationNumber))
                                .defaultField("entity_id.rootExtensionTxt")
                                .defaultOperator(Operator.AND),
                        ScoreMode.Avg));

                builder.must(QueryBuilders.nestedQuery(ElasticsearchPerson.ENTITY_ID_FIELD,
                        QueryBuilders.queryStringQuery(filter.getIdentification().getIdentificationType())
                                .defaultField("entity_id.typeCd")
                                .defaultOperator(Operator.AND),
                        ScoreMode.Avg));
            }
        }

        addRecordStatusQuery(filter.getRecordStatus(), builder);

        if (filter.getDateOfBirth() != null) {
            String dobOperator = filter.getDateOfBirthOperator();
            String dobString = FlexibleInstantConverter.toString(filter.getDateOfBirth());
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
     * Adds the record status to the query builder. If no record status is
     * specified, throw a QueryException.
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
     * Generates a patient id and localId. Posts the request along with Id's to
     * Kafka
     */
    public PatientEventResponse sendCreatePatientRequest(PatientInput input) {
        // create 'create patient' message and post to kafka
        var requestId = getRequestId();
        var user = SecurityUtil.getUserDetails();

        var createEvent = this.createRequestResolver.create(user.getId(), requestId, input);
        return sendPatientEvent(createEvent);
    }

    public PatientEventResponse updatePatientGeneralInfo(GeneralInfoInput input) {
        var user = SecurityUtil.getUserDetails();
        var updateGeneralInfoEvent = GeneralInfoInput.toRequest(user.getId(), getRequestId(), input);
        return sendPatientEvent(updateGeneralInfoEvent);
    }

    public PatientEventResponse addPatientName(NameInput input) {
        var user = SecurityUtil.getUserDetails();
        var event = NameInput.toAddRequest(user.getId(), getRequestId(), input);
        return sendPatientEvent(event);
    }

    public PatientEventResponse updatePatientName(NameInput input) {
        var user = SecurityUtil.getUserDetails();
        var event = NameInput.toUpdateRequest(user.getId(), getRequestId(), input);
        return sendPatientEvent(event);
    }

    public PatientEventResponse deletePatientName(Long patientId, Short personNameSeq) {
        var user = SecurityUtil.getUserDetails();
        var event = new PatientRequest.DeleteName(getRequestId(), patientId, personNameSeq, user.getId());
        return sendPatientEvent(event);
    }

    public PatientEventResponse updateAdministrative(AdministrativeInput input) {
        var user = SecurityUtil.getUserDetails();
        var event = AdministrativeInput.toRequest(user.getId(), getRequestId(), input);
        return sendPatientEvent(event);
    }

    public PatientEventResponse updatePatientSexBirth(SexAndBirthInput input) {
        var user = SecurityUtil.getUserDetails();
        var updateSexAndBirthEvent = SexAndBirthInput.toRequest(user.getId(), getRequestId(), input);
        return sendPatientEvent(updateSexAndBirthEvent);
    }

    public PatientEventResponse updateMortality(MortalityInput input) {
        var user = SecurityUtil.getUserDetails();
        var updateMortalityEvent = MortalityInput.toRequest(user.getId(), getRequestId(), input);
        return sendPatientEvent(updateMortalityEvent);
    }

    public PatientEventResponse sendDeletePatientEvent(Long patientId) {
        var user = SecurityUtil.getUserDetails();
        var deleteEvent = new PatientRequest.Delete(getRequestId(), patientId, user.getId());
        return sendPatientEvent(deleteEvent);
    }

    private PatientEventResponse sendPatientEvent(PatientRequest request) {
        producer.requestPatientEventEnvelope(request);
        return new PatientEventResponse(request.requestId(), request.patientId());
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
                case "relevance":
                    sorts.add(SortBuilders.scoreSort());
                    break;
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

    private String getRequestId() {
        return String.format(Constants.APP_ID + "_%s", UUID.randomUUID());
    }
}
