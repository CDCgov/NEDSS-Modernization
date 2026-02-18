package gov.cdc.nbs.patient.file.demographics.address;

import gov.cdc.nbs.patient.identifier.PatientIdentifier;
import gov.cdc.nbs.testing.support.Active;
import io.cucumber.java.en.Then;
import org.springframework.test.web.servlet.ResultActions;

public class PatientFileAddressDemographicSteps {

  private final PatientFileAddressDemographicRequester requester;

  private final Active<PatientIdentifier> activePatient;
  private final Active<ResultActions> response;

  PatientFileAddressDemographicSteps(
      final PatientFileAddressDemographicRequester requester,
      final Active<PatientIdentifier> activePatient,
      final Active<ResultActions> response) {
    this.requester = requester;
    this.activePatient = activePatient;
    this.response = response;
  }

  @Then("I view the patient's address demographics")
  public void i_view_the_patient_file_addresses() {
    this.activePatient.maybeActive().map(requester::request).ifPresent(this.response::active);
  }
}
