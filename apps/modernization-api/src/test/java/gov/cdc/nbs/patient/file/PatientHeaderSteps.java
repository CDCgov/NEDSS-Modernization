package gov.cdc.nbs.patient.file;

import gov.cdc.nbs.testing.support.Active;
import io.cucumber.java.en.Then;
import org.springframework.test.web.servlet.ResultActions;

public class PatientHeaderSteps {

  private final PatientHeaderRequester requester;
  private final Active<ResultActions> response;
  Exception exception;

  PatientHeaderSteps(
      final PatientHeaderRequester requester,
      final Active<ResultActions> response) {
    this.requester = requester;
    this.response = response;
  }

  @Then("I view the Patient File Header")
  public void i_view_the_patient_file_header(long patientId) {
    try {
      this.response.active(
          this.requester.request(patientId));
    } catch (Exception thrown) {
      this.exception = thrown;
    }

  }
}
