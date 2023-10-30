package gov.cdc.nbs.patient.search;

import gov.cdc.nbs.authentication.UserService;
import gov.cdc.nbs.config.security.SecurityUtil;
import gov.cdc.nbs.entity.elasticsearch.ElasticsearchPerson;
import gov.cdc.nbs.entity.enums.RecordStatus;
import gov.cdc.nbs.exception.QueryException;
import gov.cdc.nbs.graphql.GraphQLPage;
import gov.cdc.nbs.graphql.filter.PatientFilter;
import gov.cdc.nbs.patient.identifier.PatientLocalIdentifierResolver;
import gov.cdc.nbs.time.FlexibleInstantConverter;
import graphql.com.google.common.collect.Ordering;
import org.apache.commons.codec.language.Soundex;
import org.apache.lucene.search.join.ScoreMode;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.Operator;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.sort.SortBuilder;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import static gov.cdc.nbs.config.security.SecurityUtil.BusinessObjects.PATIENT;
import static gov.cdc.nbs.config.security.SecurityUtil.Operations.FINDINACTIVE;

@Service
public class PatientSearcher {
  private static final float FIRST_NAME_PRIMARY_BOOST = 10.0f;
  private static final float FIRST_NAME_NON_PRIMARY_BOOST = 1.0f;
  private static final float FIRST_NAME_SOUNDEX_BOOST = 0.0f;
  private static final float LAST_NAME_PRIMARY_BOOST = 10.0f;
  private static final float LAST_NAME_NON_PRIMARY_BOOST = 1.0f;
  private static final float LAST_NAME_SOUNDEX_BOOST = 0.0f;

  private final int maxPageSize;
  private final ElasticsearchOperations operations;
  private final UserService userService;
  private final PatientLocalIdentifierResolver resolver;
  private final PatientSearchResultFinder finder;

  public PatientSearcher(
      @Value("${nbs.search.patient.results.max}") final int maxResults,
      final ElasticsearchOperations operations,
      final UserService userService,
      final PatientLocalIdentifierResolver resolver,
      final PatientSearchResultFinder finder
  ) {
    this.maxPageSize = maxResults;
    this.operations = operations;
    this.userService = userService;
    this.resolver = resolver;
    this.finder = finder;
  }

  @SuppressWarnings({"squid:S3776", "squid:S6541"})
  // ignore high cognitive complexity as the method is simply going through the
  // passed in parameters, checking if null, and appending to the query
  public Page<PatientSearchResult> search(final PatientFilter filter, final GraphQLPage page) {
    var pageable = GraphQLPage.toPageable(page, maxPageSize);

    BoolQueryBuilder builder = QueryBuilders.boolQuery();

    builder.must(QueryBuilders.matchQuery(ElasticsearchPerson.CD_FIELD, "PAT"));

    if (filter.getId() != null) {
      String shortOrLongIdStripped = filter.getId().strip();
      if (Character.isDigit(shortOrLongIdStripped.charAt(0))) {
        try {
          long shortId = Long.parseLong(filter.getId());

          String localId = resolver.resolve(shortId);
          builder.must(QueryBuilders.matchQuery(ElasticsearchPerson.LOCAL_ID, localId));
        } catch (NumberFormatException exception) {
          // skip this criteria. it's not a short id or long id
        }
      } else {
        builder.must(QueryBuilders.matchQuery(ElasticsearchPerson.LOCAL_ID, filter.getId()));
      }
    }

    if (filter.getFirstName() != null && !filter.getFirstName().isEmpty()) {
      BoolQueryBuilder firstNameBuilder = QueryBuilders.boolQuery();

      firstNameBuilder.should(QueryBuilders.queryStringQuery(
              addWildcards(filter.getFirstName()))
          .defaultField(ElasticsearchPerson.FIRST_NM)
          .defaultOperator(Operator.AND).boost(FIRST_NAME_PRIMARY_BOOST));

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

      lastNameBuilder.should(QueryBuilders.queryStringQuery(
              addWildcards(filter.getLastName()))
          .defaultField(ElasticsearchPerson.LAST_NM)
          .defaultOperator(Operator.AND).boost(LAST_NAME_PRIMARY_BOOST));

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
      String allDigitPhoneNumber = filter.getPhoneNumber().replaceAll("\\W", "");
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

    if (filter.getGender() != null) {
      builder.must(QueryBuilders.matchQuery(ElasticsearchPerson.CURR_SEX_CD, filter.getGender()));
    }

    if (filter.getDeceased() != null) {
      builder.must(QueryBuilders.matchQuery(ElasticsearchPerson.DECEASED_IND_CD, filter.getDeceased()));
    }

    if (filter.getAddress() != null && !filter.getAddress().isEmpty()) {
      builder.must(QueryBuilders.nestedQuery(ElasticsearchPerson.ADDRESS_FIELD, QueryBuilders
              .queryStringQuery(
                  addWildcards(filter.getAddress()))
              .defaultField("address.streetAddr1")
              .defaultOperator(Operator.AND),
          ScoreMode.Avg));
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

      String value = filter.getZip();

      String adjusted = value.length() < 5 ? addWildcards(value) : value;

      builder.must(
          QueryBuilders.nestedQuery(ElasticsearchPerson.ADDRESS_FIELD,
              QueryBuilders.queryStringQuery(adjusted)
                  .defaultField("address.zip")
                  .defaultOperator(Operator.AND),
              ScoreMode.Avg
          )
      );
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

    identificationCriteria(filter).ifPresent(builder::must);

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

    List<Long> ids = elasticsearchPersonSearchHits
        .stream()
        .map(SearchHit::getContent)
        .map(ElasticsearchPerson::getPersonUid)
        .toList();

    List<PatientSearchResult> results = finder.find(ids)
        .stream()
        .sorted(Ordering.explicit(ids).onResultOf(PatientSearchResult::patient))
        .toList();

    return new PageImpl<>(
        results,
        pageable,
        elasticsearchPersonSearchHits.getTotalHits()
    );
  }

  private Optional<QueryBuilder> identificationCriteria(final PatientFilter filter) {

    PatientFilter.Identification identification = filter.getIdentification();

    String type = identification.getIdentificationType();
    String value = identification.getIdentificationNumber();

    if (type != null && value != null) {

      String adjusted = value.replaceAll("\\W", "");

      return Optional.of(
          QueryBuilders.nestedQuery(
              ElasticsearchPerson.ENTITY_ID_FIELD,
              QueryBuilders.boolQuery()
                  .must(
                      QueryBuilders.matchQuery("entity_id.typeCd", type)
                          .operator(Operator.AND)
                  )
                  .must(
                      QueryBuilders.queryStringQuery(addWildcards(adjusted))
                          .defaultField("entity_id.rootExtensionTxt")
                  )
              ,
              ScoreMode.Avg
          ));
    }

    return Optional.empty();

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
          sorts.add(SortBuilders.fieldSort(ElasticsearchPerson.LAST_NM)
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

}
