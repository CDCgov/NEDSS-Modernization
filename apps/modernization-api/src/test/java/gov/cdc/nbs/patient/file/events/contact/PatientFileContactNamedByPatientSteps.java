package gov.cdc.nbs.patient.file.events.contact;

import gov.cdc.nbs.patient.identifier.PatientIdentifier;
import gov.cdc.nbs.testing.support.Active;
import io.cucumber.java.en.When;
import org.springframework.test.web.servlet.ResultActions;

public class PatientFileContactNamedByPatientSteps {

  private final Active<PatientIdentifier> activePatient;
  private final PatientFileContactNamedByPatientRequester requester;
  private final Active<ResultActions> response;

  PatientFileContactNamedByPatientSteps(
      final Active<PatientIdentifier> activePatient,
      final PatientFileContactNamedByPatientRequester requester,
      final Active<ResultActions> response
  ) {
    this.activePatient = activePatient;
    this.requester = requester;
    this.response = response;
  }

  @When("I view the contacts named by the patient")
  public void view() {
    this.activePatient.maybeActive()
        .map(requester::request)
        .ifPresent(response::active);
  }
}
