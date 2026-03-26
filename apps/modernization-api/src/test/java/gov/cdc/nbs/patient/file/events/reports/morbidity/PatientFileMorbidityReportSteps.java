package gov.cdc.nbs.patient.file.events.reports.morbidity;

import gov.cdc.nbs.patient.identifier.PatientIdentifier;
import gov.cdc.nbs.testing.support.Active;
import io.cucumber.java.en.When;
import org.springframework.test.web.servlet.ResultActions;

public class PatientFileMorbidityReportSteps {

  private final Active<PatientIdentifier> activePatient;
  private final PatientFileMorbidityReportRequester requester;
  private final Active<ResultActions> response;

  PatientFileMorbidityReportSteps(
      final Active<PatientIdentifier> activePatient,
      final PatientFileMorbidityReportRequester requester,
      final Active<ResultActions> response) {
    this.activePatient = activePatient;
    this.requester = requester;
    this.response = response;
  }

  @When("I view the morbidity reports for the patient")
  public void view() {
    this.activePatient.maybeActive().map(requester::request).ifPresent(response::active);
  }
}
