package gov.cdc.nbs.patient.file.events.contact;

import gov.cdc.nbs.patient.identifier.PatientIdentifier;
import gov.cdc.nbs.testing.support.Active;
import io.cucumber.java.en.When;
import org.springframework.test.web.servlet.ResultActions;

public class PatientFilePatientNamedByContactSteps {

  private final Active<PatientIdentifier> activePatient;
  private final PatientFilePatientNamedByContactRequester requester;
  private final Active<ResultActions> response;

  PatientFilePatientNamedByContactSteps(
      final Active<PatientIdentifier> activePatient,
      final PatientFilePatientNamedByContactRequester requester,
      final Active<ResultActions> response) {
    this.activePatient = activePatient;
    this.requester = requester;
    this.response = response;
  }

  @When("I view the contacts that named the patient")
  public void view() {
    this.activePatient.maybeActive().map(requester::request).ifPresent(response::active);
  }
}
