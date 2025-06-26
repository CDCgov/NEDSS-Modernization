package gov.cdc.nbs.patient.file.events.reports.laboratory;

import gov.cdc.nbs.patient.identifier.PatientIdentifier;
import gov.cdc.nbs.testing.support.Active;
import io.cucumber.java.en.When;
import org.springframework.test.web.servlet.ResultActions;

public class PatientFileLaboratoryReportSteps {

  private final Active<PatientIdentifier> activePatient;
  private final PatientFileLaboratoryReportRequester requester;
  private final Active<ResultActions> response;

  PatientFileLaboratoryReportSteps(
      final Active<PatientIdentifier> activePatient,
      final PatientFileLaboratoryReportRequester requester,
      final Active<ResultActions> response
  ) {
    this.activePatient = activePatient;
    this.requester = requester;
    this.response = response;
  }

  @When("I view the lab(oratory) reports for the patient")
  public void view() {
    this.activePatient.maybeActive()
        .map(requester::request)
        .ifPresent(response::active);
  }
}
