package gov.cdc.nbs.patient.file.demographics.general;

import gov.cdc.nbs.patient.identifier.PatientIdentifier;
import gov.cdc.nbs.testing.support.Active;
import io.cucumber.java.en.Then;
import org.springframework.test.web.servlet.ResultActions;

public class PatientFileGeneralInformationDemographicSteps {

  private final PatientFileGeneralInformationDemographicRequester requester;

  private final Active<PatientIdentifier> activePatient;
  private final Active<ResultActions> response;

  PatientFileGeneralInformationDemographicSteps(
      final PatientFileGeneralInformationDemographicRequester requester,
      final Active<PatientIdentifier> activePatient,
      final Active<ResultActions> response) {
    this.requester = requester;
    this.activePatient = activePatient;
    this.response = response;
  }

  @Then("I view the patient's general information demographics")
  public void races() {
    this.activePatient.maybeActive().map(requester::request).ifPresent(this.response::active);
  }
}
