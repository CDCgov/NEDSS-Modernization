package gov.cdc.nbs.patient.search;

import java.util.Optional;
import java.util.stream.Stream;
import org.springframework.stereotype.Component;
import co.elastic.clients.elasticsearch._types.query_dsl.MatchQuery;
import co.elastic.clients.elasticsearch._types.query_dsl.Query;
import co.elastic.clients.elasticsearch._types.query_dsl.QueryVariant;

@Component
class PatientEventQueryResolver {
  private static final String MORBIDITY_REPORT_ID = "morbidity_report_ids";
  private static final String DOCUMENT_ID = "document_ids";
  private static final String STATE_CASE_ID = "state_case_ids";
  private static final String ABC_CASE_ID = "abcs_case_ids";
  private static final String LAB_REPORT_ID = "lab_report_ids";

  Stream<Query> resolve(final PatientFilter criteria) {
    return Stream.of(
        applyMorbidityIdCriteria(criteria),
        applyDocumentIdCriteria(criteria),
        applyStateCaseIdCriteria(criteria),
        applyAbcCaseIdCriteria(criteria),
        applyLabIdCriteria(criteria))
        .flatMap(Optional::stream)
        .map(QueryVariant::_toQuery);
  }

  private Optional<QueryVariant> applyMorbidityIdCriteria(final PatientFilter criteria) {
    return criteria.maybeMorbidityId()
        .map(identifier -> MatchQuery.of(match -> match.field(MORBIDITY_REPORT_ID).query(criteria.getMorbidityId())));
  }

  private Optional<QueryVariant> applyDocumentIdCriteria(final PatientFilter criteria) {
    return criteria.maybeDocumentId()
        .map(identifier -> MatchQuery.of(match -> match.field(DOCUMENT_ID).query(criteria.getDocumentId())));
  }

  private Optional<QueryVariant> applyStateCaseIdCriteria(final PatientFilter criteria) {
    return criteria.maybeStateCaseId()
        .map(identifier -> MatchQuery.of(match -> match.field(STATE_CASE_ID).query(criteria.getStateCaseId())));
  }

  private Optional<QueryVariant> applyAbcCaseIdCriteria(final PatientFilter criteria) {
    return criteria.maybeAbcCaseId()
        .map(identifier -> MatchQuery.of(match -> match.field(ABC_CASE_ID).query(criteria.getAbcCaseId())));
  }


  private Optional<QueryVariant> applyLabIdCriteria(final PatientFilter criteria) {
    return criteria.maybeLabReportId()
        .map(identifier -> MatchQuery.of(match -> match.field(LAB_REPORT_ID).query(criteria.getLabReportId())));
  }


}
