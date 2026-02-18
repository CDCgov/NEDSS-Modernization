package gov.cdc.nbs.patient.file.demographics.mortality;

import gov.cdc.nbs.patient.identifier.PatientIdentifier;
import gov.cdc.nbs.testing.support.Active;
import io.cucumber.java.en.Then;
import org.springframework.test.web.servlet.ResultActions;

public class PatientFileMortalityDemographicSteps {

  private final PatientFileMortalityDemographicRequester requester;

  private final Active<PatientIdentifier> activePatient;
  private final Active<ResultActions> response;

  PatientFileMortalityDemographicSteps(
      final PatientFileMortalityDemographicRequester requester,
      final Active<PatientIdentifier> activePatient,
      final Active<ResultActions> response) {
    this.requester = requester;
    this.activePatient = activePatient;
    this.response = response;
  }

  @Then("I view the patient's mortality demographics")
  public void mortality() {
    this.activePatient.maybeActive().map(requester::request).ifPresent(this.response::active);
  }
}
