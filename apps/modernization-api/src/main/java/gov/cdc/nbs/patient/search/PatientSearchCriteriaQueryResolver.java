package gov.cdc.nbs.patient.search;

import co.elastic.clients.elasticsearch._types.query_dsl.BoolQuery;
import co.elastic.clients.elasticsearch._types.query_dsl.Query;
import java.util.stream.Stream;
import org.springframework.stereotype.Component;

@Component
class PatientSearchCriteriaQueryResolver {
  private final PatientDemographicQueryResolver demographicQueryResolver;
  private final PatientEventQueryResolver eventQueryResolver;

  PatientSearchCriteriaQueryResolver(
      final PatientDemographicQueryResolver demographicQueryResolver,
      final PatientEventQueryResolver eventQueryResolver) {
    this.demographicQueryResolver = demographicQueryResolver;
    this.eventQueryResolver = eventQueryResolver;
  }

  Query resolve(final PatientSearchCriteria criteria) {
    return Stream.concat(
            demographicQueryResolver.resolve(criteria), eventQueryResolver.resolve(criteria))
        .reduce(
            new BoolQuery.Builder(),
            BoolQuery.Builder::must,
            (one, two) -> one.must(two.build().must()))
        .build()
        ._toQuery();
  }
}
