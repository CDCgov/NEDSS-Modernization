package gov.cdc.nbs.patient.search;

import gov.cdc.nbs.event.document.CaseReportIdentifier;
import gov.cdc.nbs.event.investigation.AbcCaseIdentifier;
import gov.cdc.nbs.event.investigation.CityCountyCaseIdentifier;
import gov.cdc.nbs.event.investigation.StateCaseIdentifier;
import gov.cdc.nbs.event.report.lab.LabReportIdentifier;
import gov.cdc.nbs.event.report.morbidity.MorbidityReportIdentifier;
import gov.cdc.nbs.testing.support.Active;
import io.cucumber.java.en.Given;

public class PatientSearchEventIdSteps {
  private final Active<PatientFilter> activeCriteria;
  private final Active<MorbidityReportIdentifier> activeMorbidityReport;
  private final Active<LabReportIdentifier> activeLabReport;
  private final Active<CaseReportIdentifier> activeCaseReport;
  private final Active<StateCaseIdentifier> activeStateCase;
  private final Active<AbcCaseIdentifier> activeAbcCase;
  private final Active<CityCountyCaseIdentifier> activeCityCountyCase;

  PatientSearchEventIdSteps(
      final Active<PatientFilter> activeCriteria,
      final Active<LabReportIdentifier> activeLabReport,
      final Active<CaseReportIdentifier> activeCaseReport,
      final Active<StateCaseIdentifier> activeStateCase,
      final Active<AbcCaseIdentifier> activeAbcCase,
      final Active<CityCountyCaseIdentifier> activeCityCountyCase,
      final Active<MorbidityReportIdentifier> activeMorbidityReport) {
    this.activeCriteria = activeCriteria;
    this.activeMorbidityReport = activeMorbidityReport;
    this.activeLabReport = activeLabReport;
    this.activeStateCase = activeStateCase;
    this.activeAbcCase = activeAbcCase;
    this.activeCityCountyCase = activeCityCountyCase;
    this.activeCaseReport = activeCaseReport;
  }

  @Given("I would like to search for a patient using the Morbidity Report ID")
  public void i_would_like_to_search_for_a_patient_using_the_morbidity_report_ID() {
    this.activeMorbidityReport.maybeActive()
        .map(MorbidityReportIdentifier::local)
        .ifPresent(
            identifier -> this.activeCriteria.active(criteria -> criteria.withMorbidityId(identifier)));
  }

  @Given("I would like to search for a patient using the Document ID")
  public void i_would_like_to_search_for_a_patient_using_the_document_ID() {
    this.activeCaseReport.maybeActive()
        .map(CaseReportIdentifier::local)
        .ifPresent(
            identifier -> this.activeCriteria.active(criteria -> criteria.withDocumentId(identifier)));
  }

  @Given("I would like to search for a patient using the State Case ID")
  public void i_would_like_to_search_for_a_patient_using_the_state_case_ID() {
    this.activeStateCase.maybeActive()
        .map(StateCaseIdentifier::identifier)
        .ifPresent(
            identifier -> this.activeCriteria.active(criteria -> criteria.withStateCaseId(identifier)));
  }

  @Given("I would like to search for a patient using the ABC Case ID")
  public void i_would_like_to_search_for_a_patient_using_the_abc_case_ID() {
    this.activeAbcCase.maybeActive()
        .map(AbcCaseIdentifier::identifier)
        .ifPresent(
            identifier -> this.activeCriteria.active(criteria -> criteria.withAbcCaseId(identifier)));
  }

  @Given("I would like to search for a patient using the County Case ID")
  public void i_would_like_to_search_for_a_patient_using_the_county_case_ID() {
    this.activeCityCountyCase.maybeActive()
        .map(CityCountyCaseIdentifier::identifier)
        .ifPresent(
            identifier -> this.activeCriteria.active(criteria -> criteria.withCityCountyCaseId(identifier)));
  }

  @Given("I would like to search for a patient using the Lab Report ID")
  public void i_would_like_to_search_for_a_patient_using_the_lab_report_ID() {
    this.activeLabReport.maybeActive()
        .map(LabReportIdentifier::local)
        .ifPresent(
            identifier -> this.activeCriteria.active(criteria -> criteria.withLabReportId(identifier)));
  }
}
