package gov.cdc.nbs.patient.search;

import co.elastic.clients.elasticsearch._types.query_dsl.BoolQuery;
import co.elastic.clients.elasticsearch._types.query_dsl.Query;
import org.springframework.stereotype.Component;

import java.util.stream.Stream;

@Component
class PatientSearchCriteriaFilterResolver {

  private final PatientSearchDemographicCriteriaFilterResolver demographicFilterResolver;
  private final PatientSearchEventIDCriteriaQueryResolver eventIDFilterResolver;

  PatientSearchCriteriaFilterResolver(
      final PatientSearchDemographicCriteriaFilterResolver demographicFilterResolver,
      final PatientSearchEventIDCriteriaQueryResolver eventIDFilterResolver
  ) {
    this.demographicFilterResolver = demographicFilterResolver;
    this.eventIDFilterResolver = eventIDFilterResolver;
  }

  Query resolve(final PatientFilter criteria) {
    return Stream.concat(
            eventIDFilterResolver.resolve(criteria),
            demographicFilterResolver.resolve(criteria)
        ).reduce(
            new BoolQuery.Builder(),
            BoolQuery.Builder::must,
            (one, two) -> one.must(two.build().must()))
        .build()._toQuery();
  }



}
