package gov.cdc.nbs.patient.search;

import co.elastic.clients.elasticsearch._types.query_dsl.QueryVariant;
import gov.cdc.nbs.search.criteria.text.TextCriteria;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.stream.Stream;

import static gov.cdc.nbs.search.criteria.text.TextCriteriaNestedQueryResolver.*;

@Component
class PatientNameDemographicQueryResolver {
  private static final String NAMES = "name";
  private static final String LAST_NAME = "name.lastNm";
  private static final String LAST_NAME_SOUNDEX = "name.lastNmSndx.keyword";

  Stream<QueryVariant> resolve(final PatientFilter criteria) {
    return criteria.maybeName().stream().flatMap(this::resolveNameCriteria);
  }

  private Stream<QueryVariant> resolveNameCriteria(final PatientFilter.NameCriteria criteria) {
    return Stream.of(
            applyLastNameEquals(criteria),
            applyLastNameNotEquals(criteria),
            applyLastNameContains(criteria),
            applyLastNameStartsWith(criteria),
            applyLastNameSoundsLike(criteria)
        )
        .flatMap(Optional::stream);
  }

  private Optional<QueryVariant> applyLastNameEquals(final PatientFilter.NameCriteria criteria) {
    return criteria.maybeLast()
        .flatMap(TextCriteria::maybeEquals)
        .map(value -> equalTo(NAMES, LAST_NAME, value));
  }

  private Optional<QueryVariant> applyLastNameNotEquals(final PatientFilter.NameCriteria criteria) {
    return criteria.maybeLast()
        .flatMap(TextCriteria::maybeNot)
        .map(value -> notEquals(NAMES, LAST_NAME, value));
  }

  private Optional<QueryVariant> applyLastNameContains(final PatientFilter.NameCriteria criteria) {
    return criteria.maybeLast()
        .flatMap(TextCriteria::maybeContains)
        .map(value -> contains(NAMES, LAST_NAME, value));
  }

  private Optional<QueryVariant> applyLastNameStartsWith(final PatientFilter.NameCriteria criteria) {
    return criteria.maybeLast()
        .flatMap(TextCriteria::maybeStartsWith)
        .map(value -> startsWith(NAMES, LAST_NAME, value));
  }

  private Optional<QueryVariant> applyLastNameSoundsLike(final PatientFilter.NameCriteria criteria) {
    return criteria.maybeLast()
        .flatMap(TextCriteria::maybeSoundsLike)
        .map(value -> soundLike(NAMES, LAST_NAME_SOUNDEX, value));
  }

}
