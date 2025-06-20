package gov.cdc.nbs.patient.file.demographics.sex_birth;


import gov.cdc.nbs.patient.identifier.PatientIdentifier;
import gov.cdc.nbs.testing.support.Active;
import io.cucumber.java.en.Then;
import org.springframework.test.web.servlet.ResultActions;

public class PatientFileSexBirthDemographicSteps {

  private final PatientFileSexBirthDemographicRequester requester;

  private final Active<PatientIdentifier> activePatient;
  private final Active<ResultActions> response;

  PatientFileSexBirthDemographicSteps(
      final PatientFileSexBirthDemographicRequester requester,
      final Active<PatientIdentifier> activePatient,
      final Active<ResultActions> response
  ) {
    this.requester = requester;
    this.activePatient = activePatient;
    this.response = response;
  }

  @Then("I view the patient's birth demographics")
  @Then("I view the patient's gender demographics")
  @Then("I view the patient's sex demographics")
  public void birth() {
    this.activePatient.maybeActive()
        .map(requester::request)
        .ifPresent(this.response::active);
  }

}
