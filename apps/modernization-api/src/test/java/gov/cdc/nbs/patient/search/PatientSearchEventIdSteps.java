package gov.cdc.nbs.patient.search;

import gov.cdc.nbs.event.report.lab.LabReportIdentifier;
import gov.cdc.nbs.event.report.morbidity.MorbidityReportIdentifier;
import gov.cdc.nbs.testing.support.Active;
import io.cucumber.java.en.Given;

public class PatientSearchEventIdSteps {
  private final Active<PatientFilter> activeCriteria;
  private final Active<MorbidityReportIdentifier> activeMorbidityReport;
  private final Active<LabReportIdentifier> activeLabReport;

  PatientSearchEventIdSteps(
      final Active<PatientFilter> activeCriteria,
      final Active<LabReportIdentifier> activeLabReport,
      final Active<MorbidityReportIdentifier> activeMorbidityReport) {
    this.activeCriteria = activeCriteria;
    this.activeMorbidityReport = activeMorbidityReport;
    this.activeLabReport = activeLabReport;
  }

  @Given("I would like to search for a patient using the Morbidity Report ID")
  public void i_would_like_to_search_for_a_patient_using_the_morbidity_report_ID() {
    this.activeMorbidityReport.maybeActive()
        .map(MorbidityReportIdentifier::local)
        .ifPresent(
            identifier -> this.activeCriteria.active(criteria -> criteria.withMorbidityId(identifier)));
  }

  @Given("I would like to search for a patient using the Lab Report ID")
  public void i_would_like_to_search_for_a_patient_using_the_lab_report_ID() {
    this.activeLabReport.maybeActive()
        .map(LabReportIdentifier::local)
        .ifPresent(
            identifier -> this.activeCriteria.active(criteria -> criteria.withLabReportId(identifier)));
  }
}
