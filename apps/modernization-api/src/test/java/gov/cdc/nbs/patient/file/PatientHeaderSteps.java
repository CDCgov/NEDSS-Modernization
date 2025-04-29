package gov.cdc.nbs.patient.file;

import gov.cdc.nbs.patient.identifier.PatientIdentifier;
import gov.cdc.nbs.testing.support.Active;
import io.cucumber.java.en.Then;
import org.springframework.test.web.servlet.ResultActions;

public class PatientHeaderSteps {

  private final Active<PatientIdentifier> activePatient;
  private final PatientHeaderRequester requester;
  private final Active<ResultActions> response;


  PatientHeaderSteps(
      final Active<PatientIdentifier> activePatient,
      final PatientHeaderRequester requester,
      final Active<ResultActions> response) {
    this.activePatient = activePatient;
    this.requester = requester;
    this.response = response;
  }

  @Then("I view the Patient File Header")
  public void i_view_the_patient_file_header(long patientId) {
    try {
      this.response.active(
          this.requester.request(patientId));
    } catch (Exception thrown) {
    }

  }
}
