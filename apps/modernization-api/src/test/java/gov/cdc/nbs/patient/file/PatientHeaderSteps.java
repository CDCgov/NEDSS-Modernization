package gov.cdc.nbs.patient.file;

import gov.cdc.nbs.patient.identifier.PatientIdentifier;
import gov.cdc.nbs.testing.support.Active;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.springframework.test.web.servlet.ResultActions;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.hamcrest.Matchers.equalTo;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

public class PatientHeaderSteps {

  private final Active<PatientIdentifier> activePatient;
  private final PatientHeaderRequester requester;
  private final Active<ResultActions> response;
  Exception exception;

  PatientHeaderSteps(
      final Active<PatientIdentifier> activePatient,
      final PatientHeaderRequester requester,
      final Active<ResultActions> response) {
    this.activePatient = activePatient;
    this.requester = requester;
    this.response = response;
  }

  @When("I view the Patient File Header")
  public void i_view_the_patient_file_header() {
    try {
      this.response.active(
          this.requester.request(activePatient.active().shortId()));
    } catch (Exception thrown) {
      this.exception = thrown;
    }
  }

  @Then("the packet is blank")
  public void the_packet_is_blank() throws Exception {
    this.response.active().andExpect(content().string(""));
  }

  @Then("the patient status is {string}")
  public void the_patient_status_is(String status) throws Exception {
    this.response.active().andExpect(jsonPath("$.status", equalTo(status)));
  }

  @Then("the patient deletability is {string}")
  public void the_patient_deletability_is(String deletability) throws Exception {
    this.response.active().andExpect(jsonPath("$.deletable", equalTo(deletability)));
  }
}
