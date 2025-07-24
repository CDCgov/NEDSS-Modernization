package gov.cdc.nbs.patient.file.events.treatment;

import gov.cdc.nbs.patient.identifier.PatientIdentifier;
import gov.cdc.nbs.testing.support.Active;
import io.cucumber.java.en.When;
import org.springframework.test.web.servlet.ResultActions;

public class PatientFileTreatmentSteps {

  private final Active<PatientIdentifier> activePatient;
  private final PatientFileTreatmentsRequester requester;
  private final Active<ResultActions> response;

  PatientFileTreatmentSteps(
      final Active<PatientIdentifier> activePatient,
      final PatientFileTreatmentsRequester requester,
      final Active<ResultActions> response
  ) {
    this.activePatient = activePatient;
    this.requester = requester;
    this.response = response;
  }

  @When("I view the treatments for the patient")
  public void view() {
    this.activePatient.maybeActive()
        .map(requester::request)
        .ifPresent(response::active);
  }
}
