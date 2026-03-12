package gov.cdc.nbs.patient.search;

import co.elastic.clients.elasticsearch._types.FieldValue;
import co.elastic.clients.elasticsearch._types.Script;
import co.elastic.clients.elasticsearch._types.query_dsl.*;
import gov.cdc.nbs.entity.enums.RecordStatus;
import gov.cdc.nbs.search.criteria.date.DateCriteria;
import gov.cdc.nbs.search.criteria.date.DateCriteria.Equals;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.springframework.stereotype.Component;

@Component
class PatientSearchCriteriaFilterResolver {

  private static final String GENDER_FIELD = "curr_sex_cd";
  private static final String STATUS = "record_status_cd";
  private static final String DECEASED = "deceased_ind_cd";
  private static final String PERSON_TYPE = "cd";
  private static final String PERSON_TYPE_PATIENT = "PAT";
  private static final String PAINLESS = "painless";

  /**
   * Translates the {@code criteria} into a {@link Query} that can be submitted to Elasticsearch.
   *
   * @param criteria The {@link PatientSearchCriteria} representing the criteria used to search for
   *     patients.
   * @return A {@link Query}
   * @throws UnresolvableSearchException when values of the criteria is known to result in no
   *     results.
   */
  Query resolve(final PatientSearchCriteria criteria) throws UnresolvableSearchException {
    return Stream.of(
            onlyPatients(),
            applyStatusCriteria(criteria),
            applyGenderCriteria(criteria),
            applyDeceasedCriteria(criteria),
            applyRaceCriteria(criteria),
            applyEthnicityCriteria(criteria),
            applyIdentificationCriteria(criteria),
            applyStateCriteria(criteria),
            applyDateOfBirthDayCriteria(criteria),
            applyDateOfBirthMonthCriteria(criteria),
            applyDateOfBirthYearCriteria(criteria),
            applyCountryCriteria(criteria))
        .flatMap(Optional::stream)
        .map(QueryVariant::_toQuery)
        .reduce(
            new BoolQuery.Builder(),
            BoolQuery.Builder::filter,
            (one, two) -> one.filter(two.build().filter()))
        .build()
        ._toQuery();
  }

  private Optional<QueryVariant> onlyPatients() {
    return Optional.of(TermQuery.of(query -> query.field(PERSON_TYPE).value(PERSON_TYPE_PATIENT)));
  }

  private Optional<QueryVariant> applyStatusCriteria(final PatientSearchCriteria criteria) {
    TermsQuery statuses =
        criteria.adjustedStatus().stream()
            .map(RecordStatus::name)
            .map(FieldValue::of)
            .collect(
                Collectors.collectingAndThen(
                    Collectors.toList(),
                    collected ->
                        TermsQuery.of(
                            query -> query.field(STATUS).terms(terms -> terms.value(collected)))));

    return Optional.of(statuses);
  }

  private Optional<QueryVariant> applyGenderCriteria(final PatientSearchCriteria criteria) {

    Optional<SearchableGender> maybeCriteria = criteria.maybeGender();

    //  filtering on gender/sex does not work like the other filters due to the gender field being a
    // coded value and not
    //  the description.
    Optional<SearchableGender> maybeFilter =
        criteria
            .maybeFilter()
            .map(PatientSearchCriteria.Filter::sex)
            .map(SearchableGender::resolve);

    if (maybeCriteria.isPresent()
        && maybeFilter.isPresent()
        && maybeCriteria.get() != maybeFilter.get()) {
      //  short circuit search, there will be no results
      throw new UnresolvableSearchException("Conflicting gender criteria and gender filter");
    }

    return maybeCriteria.or(() -> maybeFilter).map(this::resolveGenderQuery);
  }

  private QueryVariant resolveGenderQuery(final SearchableGender gender) {
    return switch (gender) {
      case UNRECOGNIZED -> throw new UnresolvableSearchException("Unrecognized gender");
      case NO_VALUE ->
          BoolQuery.of(
              bool -> bool.mustNot(not -> not.exists(exists -> exists.field(GENDER_FIELD))));
      default -> TermQuery.of(query -> query.field(GENDER_FIELD).value(gender.value()));
    };
  }

  private Optional<QueryVariant> applyDeceasedCriteria(final PatientSearchCriteria criteria) {
    if (criteria.getDeceased() != null) {
      return Optional.of(
          TermQuery.of(query -> query.field(DECEASED).value(criteria.getDeceased().value())));
    }

    return Optional.empty();
  }

  private Optional<QueryVariant> applyRaceCriteria(final PatientSearchCriteria criteria) {
    if (criteria.getRace() != null) {
      return Optional.of(
          NestedQuery.of(
              nested ->
                  nested
                      .path("race")
                      .scoreMode(ChildScoreMode.Avg)
                      .query(
                          query ->
                              query.term(
                                  term ->
                                      term.field("race.raceCategoryCd.keyword")
                                          .value(criteria.getRace())))));
    }

    return Optional.empty();
  }

  private Optional<QueryVariant> applyEthnicityCriteria(final PatientSearchCriteria criteria) {
    if (criteria.getEthnicity() != null) {
      return Optional.of(
          TermQuery.of(term -> term.field("ethnic_group_ind").value(criteria.getEthnicity())));
    }

    return Optional.empty();
  }

  private Optional<QueryVariant> applyDateOfBirthDayCriteria(final PatientSearchCriteria criteria) {
    DateCriteria dateCriteria = criteria.getBornOn();
    if (dateCriteria == null) {
      return Optional.empty();
    }
    Equals equalsDate = dateCriteria.equals();
    if (equalsDate == null || !equalsDate.isPartialDate() || equalsDate.day() == null) {
      return Optional.empty();
    }

    return Optional.of(
        ScriptQuery.of(
            q ->
                q.script(
                    Script.of(
                        script ->
                            script
                                .source(
                                    "doc['birth_time'].size()!=0 && doc['birth_time'].value.getDayOfMonth() == "
                                        + equalsDate.day())
                                .lang(PAINLESS)))));
  }

  private Optional<QueryVariant> applyDateOfBirthMonthCriteria(
      final PatientSearchCriteria criteria) {
    DateCriteria dateCriteria = criteria.getBornOn();
    if (dateCriteria == null) {
      return Optional.empty();
    }
    Equals equalsDate = dateCriteria.equals();
    if (equalsDate == null || !equalsDate.isPartialDate() || equalsDate.month() == null) {
      return Optional.empty();
    }

    return Optional.of(
        ScriptQuery.of(
            query ->
                query.script(
                    Script.of(
                        script ->
                            script
                                .source(
                                    "doc['birth_time'].size()!=0 && doc['birth_time'].value.getMonthValue() == "
                                        + equalsDate.month())
                                .lang(PAINLESS)))));
  }

  private Optional<QueryVariant> applyDateOfBirthYearCriteria(
      final PatientSearchCriteria criteria) {
    DateCriteria dateCriteria = criteria.getBornOn();
    if (dateCriteria == null) {
      return Optional.empty();
    }
    Equals equalsDate = dateCriteria.equals();
    if (equalsDate == null || !equalsDate.isPartialDate() || equalsDate.year() == null) {
      return Optional.empty();
    }

    return Optional.of(
        ScriptQuery.of(
            query ->
                query.script(
                    Script.of(
                        script ->
                            script
                                .source(
                                    "doc['birth_time'].size()!=0 && doc['birth_time'].value.getYear() == "
                                        + equalsDate.year())
                                .lang(PAINLESS)))));
  }

  private Optional<QueryVariant> applyIdentificationCriteria(final PatientSearchCriteria criteria) {

    PatientSearchCriteria.Identification identification = criteria.getIdentification();

    String type = identification.identificationType();
    String value = identification.identificationNumber();

    if (type != null && value != null) {
      return Optional.of(
          NestedQuery.of(
              nested ->
                  nested
                      .path("entity_id")
                      .scoreMode(ChildScoreMode.Avg)
                      .query(
                          query ->
                              query.bool(
                                  bool ->
                                      bool.filter(
                                          filter ->
                                              filter.term(
                                                  term ->
                                                      term.field("entity_id.typeCd.keyword")
                                                          .value(type)))))));
    }
    return Optional.empty();
  }

  private Optional<QueryVariant> applyStateCriteria(final PatientSearchCriteria criteria) {
    if (criteria.getState() != null && !criteria.getState().isEmpty()) {
      return Optional.of(
          NestedQuery.of(
              nested ->
                  nested
                      .path("address")
                      .scoreMode(ChildScoreMode.Avg)
                      .query(
                          query ->
                              query.term(
                                  term ->
                                      term.field("address.state.keyword")
                                          .value(criteria.getState())))));
    }

    return Optional.empty();
  }

  private Optional<QueryVariant> applyCountryCriteria(final PatientSearchCriteria criteria) {
    if (criteria.getCountry() != null && !criteria.getCountry().isEmpty()) {
      return Optional.of(
          NestedQuery.of(
              nested ->
                  nested
                      .path("address")
                      .scoreMode(ChildScoreMode.Avg)
                      .query(
                          query ->
                              query.term(
                                  term ->
                                      term.field("address.cntryCd.keyword")
                                          .value(criteria.getCountry())))));
    }

    return Optional.empty();
  }
}
