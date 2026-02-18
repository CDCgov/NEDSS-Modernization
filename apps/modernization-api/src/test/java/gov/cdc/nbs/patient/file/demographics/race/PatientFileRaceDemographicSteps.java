package gov.cdc.nbs.patient.file.demographics.race;

import gov.cdc.nbs.patient.identifier.PatientIdentifier;
import gov.cdc.nbs.testing.support.Active;
import io.cucumber.java.en.Then;
import org.springframework.test.web.servlet.ResultActions;

public class PatientFileRaceDemographicSteps {

  private final PatientFileRaceDemographicRequester requester;

  private final Active<PatientIdentifier> activePatient;
  private final Active<ResultActions> response;

  PatientFileRaceDemographicSteps(
      final PatientFileRaceDemographicRequester requester,
      final Active<PatientIdentifier> activePatient,
      final Active<ResultActions> response) {
    this.requester = requester;
    this.activePatient = activePatient;
    this.response = response;
  }

  @Then("I view the patient's race demographics")
  public void races() {
    this.activePatient.maybeActive().map(requester::request).ifPresent(this.response::active);
  }
}
