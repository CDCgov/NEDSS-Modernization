package gov.cdc.nbs.patient.search;

import co.elastic.clients.elasticsearch._types.query_dsl.QueryVariant;
import gov.cdc.nbs.search.criteria.text.TextCriteria;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.stream.Stream;

import static gov.cdc.nbs.search.criteria.text.TextCriteriaNestedQueryResolver.*;

@Component
class PatientLocationQueryResolver {
  private static final String ADDRESS = "address";
  private static final String STREET = "address.streetAddr1";
  private static final String CITY = "address.city";

  Stream<QueryVariant> resolve(final PatientFilter criteria) {
    return criteria.maybeLocation().stream().flatMap(this::resolveLocationCriteria);
  }

  private Stream<QueryVariant> resolveLocationCriteria(final PatientFilter.LocationCriteria criteria) {
    return Stream.of(
        applyStreetEquals(criteria),
        applyStreetContains(criteria),
        applyStreetNotEquals(criteria),
        applyCityEquals(criteria),
        applyCityContains(criteria),
        applyCityNotEquals(criteria))
        .flatMap(Optional::stream);
  }

  private Optional<QueryVariant> applyStreetEquals(final PatientFilter.LocationCriteria criteria) {
    return criteria.maybeStreet()
        .flatMap(TextCriteria::maybeEquals)
        .map(value -> equalTo(ADDRESS, STREET, value));
  }

  private Optional<QueryVariant> applyStreetNotEquals(final PatientFilter.LocationCriteria criteria) {
    return criteria.maybeStreet()
        .flatMap(TextCriteria::maybeNot)
        .map(value -> notEquals(ADDRESS, STREET, value));
  }

  private Optional<QueryVariant> applyStreetContains(final PatientFilter.LocationCriteria criteria) {
    return criteria.maybeStreet()
        .flatMap(TextCriteria::maybeContains)
        .map(value -> contains(ADDRESS, STREET, value));
  }

  private Optional<QueryVariant> applyCityEquals(final PatientFilter.LocationCriteria criteria) {
    return criteria.maybeCity()
        .flatMap(TextCriteria::maybeEquals)
        .map(value -> equalTo(ADDRESS, CITY, value));
  }

  private Optional<QueryVariant> applyCityNotEquals(final PatientFilter.LocationCriteria criteria) {
    return criteria.maybeCity()
        .flatMap(TextCriteria::maybeNot)
        .map(value -> notEquals(ADDRESS, CITY, value));
  }

  private Optional<QueryVariant> applyCityContains(final PatientFilter.LocationCriteria criteria) {
    return criteria.maybeCity()
        .flatMap(TextCriteria::maybeContains)
        .map(value -> contains(ADDRESS, CITY, value));
  }

}
