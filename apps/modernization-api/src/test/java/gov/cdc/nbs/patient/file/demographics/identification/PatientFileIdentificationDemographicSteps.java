package gov.cdc.nbs.patient.file.demographics.identification;

import gov.cdc.nbs.patient.identifier.PatientIdentifier;
import gov.cdc.nbs.testing.support.Active;
import io.cucumber.java.en.Then;
import org.springframework.test.web.servlet.ResultActions;

public class PatientFileIdentificationDemographicSteps {

  private final PatientFileIdentificationDemographicRequester requester;

  private final Active<PatientIdentifier> activePatient;
  private final Active<ResultActions> response;

  PatientFileIdentificationDemographicSteps(
      final PatientFileIdentificationDemographicRequester requester,
      final Active<PatientIdentifier> activePatient,
      final Active<ResultActions> response) {
    this.requester = requester;
    this.activePatient = activePatient;
    this.response = response;
  }

  @Then("I view the patient's identification demographics")
  public void i_view_the_patient_file_administration() {
    this.activePatient.maybeActive().map(requester::request).ifPresent(this.response::active);
  }
}
