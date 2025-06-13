package gov.cdc.nbs.patient.file.demographics.ethnicity;


import gov.cdc.nbs.patient.identifier.PatientIdentifier;
import gov.cdc.nbs.testing.support.Active;
import io.cucumber.java.en.Then;
import org.springframework.test.web.servlet.ResultActions;

public class PatientFileEthnicityDemographicSteps {

  private final PatientFileEthnicityDemographicRequester requester;

  private final Active<PatientIdentifier> activePatient;
  private final Active<ResultActions> response;

  PatientFileEthnicityDemographicSteps(
      final PatientFileEthnicityDemographicRequester requester,
      final Active<PatientIdentifier> activePatient,
      final Active<ResultActions> response
  ) {
    this.requester = requester;
    this.activePatient = activePatient;
    this.response = response;
  }

  @Then("I view the patient's ethnicity demographics")
  public void races() {
    this.activePatient.maybeActive()
        .map(requester::request)
        .ifPresent(this.response::active);
  }

}
