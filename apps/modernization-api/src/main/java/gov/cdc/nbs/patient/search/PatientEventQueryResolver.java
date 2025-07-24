package gov.cdc.nbs.patient.search;

import java.util.Optional;
import java.util.stream.Stream;
import org.springframework.stereotype.Component;
import co.elastic.clients.elasticsearch._types.query_dsl.MatchQuery;
import co.elastic.clients.elasticsearch._types.query_dsl.Query;
import co.elastic.clients.elasticsearch._types.query_dsl.QueryVariant;
import gov.cdc.nbs.search.AdjustStrings;

@Component
class PatientEventQueryResolver {
  private static final String MORBIDITY_REPORTS = "morbidity_report_ids";
  private static final String DOCUMENTS = "document_ids";
  private static final String STATE_CASES = "state_case_ids";
  private static final String ABC_CASES = "abcs_case_ids";
  private static final String CITY_COUNTY_CASES = "city_case_ids";
  private static final String NOTIFICATIONS = "notification_ids";
  private static final String TREATMENTS = "treatment_ids";
  private static final String VACCINATIONS = "vaccination_ids";
  private static final String INVESTIGATIONS = "investigation_ids";
  private static final String LAB_REPORTS = "lab_report_ids";
  private static final String ACCESSIONS = "accession_ids";

  Stream<Query> resolve(final PatientSearchCriteria criteria) {
    return Stream.of(
        applyMorbidityCriteria(criteria),
        applyDocumentCriteria(criteria),
        applyStateCaseCriteria(criteria),
        applyAbcCaseCriteria(criteria),
        applyCityCountyCaseCriteria(criteria),
        applyNotificationCriteria(criteria),
        applyTreatmentCriteria(criteria),
        applyVaccinationCriteria(criteria),
        applyInvestigationCriteria(criteria),
        applyLabCriteria(criteria),
        applyAccessionNumberCriteria(criteria))
        .flatMap(Optional::stream)
        .map(QueryVariant::_toQuery);
  }

  private Optional<QueryVariant> applyMorbidityCriteria(final PatientSearchCriteria criteria) {
    return criteria.maybeMorbidity()
        .map(identifier -> MatchQuery.of(match -> match.field(MORBIDITY_REPORTS).query(criteria.getMorbidity())));
  }

  private Optional<QueryVariant> applyDocumentCriteria(final PatientSearchCriteria criteria) {
    return criteria.maybeDocument()
        .map(identifier -> MatchQuery.of(match -> match.field(DOCUMENTS).query(criteria.getDocument())));
  }

  private Optional<QueryVariant> applyStateCaseCriteria(final PatientSearchCriteria criteria) {
    return criteria.maybeStateCase()
        .map(identifier -> MatchQuery.of(match -> match.field(STATE_CASES).query(criteria.getStateCase())));
  }

  private Optional<QueryVariant> applyAbcCaseCriteria(final PatientSearchCriteria criteria) {
    return criteria.maybeAbcCase()
        .map(identifier -> MatchQuery.of(match -> match.field(ABC_CASES).query(criteria.getAbcCase())));
  }

  private Optional<QueryVariant> applyCityCountyCaseCriteria(final PatientSearchCriteria criteria) {
    return criteria.maybeCityCountyCase()
        .map(identifier -> MatchQuery
            .of(match -> match.field(CITY_COUNTY_CASES).query(criteria.getCityCountyCase())));
  }

  private Optional<QueryVariant> applyNotificationCriteria(final PatientSearchCriteria criteria) {
    return criteria.maybeNotification()
        .map(identifier -> MatchQuery
            .of(match -> match.field(NOTIFICATIONS).query(criteria.getNotification())));
  }

  private Optional<QueryVariant> applyTreatmentCriteria(final PatientSearchCriteria criteria) {
    return criteria.maybeTreatment()
        .map(identifier -> MatchQuery
            .of(match -> match.field(TREATMENTS).query(criteria.getTreatment())));
  }

  private Optional<QueryVariant> applyVaccinationCriteria(final PatientSearchCriteria criteria) {
    return criteria.maybeVaccination()
        .map(identifier -> MatchQuery
            .of(match -> match.field(VACCINATIONS).query(criteria.getVaccination())));
  }

  private Optional<QueryVariant> applyInvestigationCriteria(final PatientSearchCriteria criteria) {
    return criteria.maybeInvestigation()
        .map(identifier -> MatchQuery
            .of(match -> match.field(INVESTIGATIONS).query(criteria.getInvestigation())));
  }

  private Optional<QueryVariant> applyLabCriteria(final PatientSearchCriteria criteria) {
    return criteria.maybeLabReport()
        .map(identifier -> MatchQuery.of(match -> match.field(LAB_REPORTS).query(criteria.getLabReport())));
  }

  private Optional<QueryVariant> applyAccessionNumberCriteria(final PatientSearchCriteria criteria) {
    return criteria.maybeAccessionNumber()
        .map(AdjustStrings::withoutSpecialCharacters)
        .map(identifier -> MatchQuery.of(match -> match.field(ACCESSIONS).query(identifier)));
  }

}
