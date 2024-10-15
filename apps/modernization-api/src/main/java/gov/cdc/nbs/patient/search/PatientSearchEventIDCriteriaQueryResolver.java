package gov.cdc.nbs.patient.search;

import co.elastic.clients.elasticsearch._types.query_dsl.Query;
import co.elastic.clients.elasticsearch._types.query_dsl.QueryVariant;
import co.elastic.clients.elasticsearch._types.query_dsl.TermQuery;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.stream.Stream;

@Component
class PatientSearchEventIDCriteriaQueryResolver {

  Stream<Query> resolve(final PatientFilter criteria) {
    return Stream.of(
            applyMorbidityReport(criteria)
        ).flatMap(Optional::stream)
        .map(QueryVariant::_toQuery);
  }

  private Optional<QueryVariant> applyMorbidityReport(final PatientFilter criteria) {
    return criteria.maybeMorbidityReport()
        .map(
            identifier -> TermQuery.of(
                term -> term.field("morbidity_report_ids")
                    .value(identifier)
            )
        );
  }

}
