package gov.cdc.nbs.event.report.lab;

import gov.cdc.nbs.patient.PatientMother;
import gov.cdc.nbs.patient.identifier.PatientIdentifier;
import gov.cdc.nbs.testing.support.Active;
import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public class LabReportSteps {

  private final Active<PatientIdentifier> activePatient;

  private final Active<LabReportIdentifier> activeLabReport;
  private final LabReportMother reportMother;

  public LabReportSteps(
      final Active<PatientIdentifier> activePatient,
      final Active<LabReportIdentifier> activeLabReport,
      final LabReportMother reportMother
  ) {
    this.activePatient = activePatient;
    this.activeLabReport = activeLabReport;
    this.reportMother = reportMother;
  }

  @Before("@lab-report")
  public void clean() {
    this.reportMother.reset();
  }

  @Given("the patient has a lab report")
  public void the_patient_has_a_lab_report() {
    activePatient.maybeActive()
        .ifPresent(reportMother::labReport);
  }

  @Given("the lab report has been processed")
  public void the_lab_report_has_been_processed() {

  }

  @Given("the patient has an unprocessed lab report")
  public void patient_has_an_unprocessed_lab_report() {
    activePatient.maybeActive()
        .ifPresent(reportMother::labReport);
  }
}
