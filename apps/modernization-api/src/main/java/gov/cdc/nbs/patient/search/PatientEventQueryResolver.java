package gov.cdc.nbs.patient.search;

import java.util.Optional;
import java.util.stream.Stream;
import org.springframework.stereotype.Component;
import co.elastic.clients.elasticsearch._types.query_dsl.MatchQuery;
import co.elastic.clients.elasticsearch._types.query_dsl.Query;
import co.elastic.clients.elasticsearch._types.query_dsl.QueryVariant;

@Component
class PatientEventQueryResolver {
  private static final String LAB_REPORT_ID = "lab_report_ids";
  private static final String MORBIDITY_REPORT_ID = "morbidity_report_ids";

  Stream<Query> resolve(final PatientFilter criteria) {
    return Stream.of(
        applyLabIdCriteria(criteria),
        applyMorbidityIdCriteria(criteria))
        .flatMap(Optional::stream)
        .map(QueryVariant::_toQuery);
  }

  private Optional<QueryVariant> applyLabIdCriteria(final PatientFilter criteria) {
    return criteria.maybeLabReportId()
        .map(identifier -> MatchQuery.of(match -> match.field(LAB_REPORT_ID).query(criteria.getLabReportId())));
  }

  private Optional<QueryVariant> applyMorbidityIdCriteria(final PatientFilter criteria) {
    return criteria.maybeMorbidityId()
        .map(identifier -> MatchQuery.of(match -> match.field(MORBIDITY_REPORT_ID).query(criteria.getMorbidityId())));
  }

}
