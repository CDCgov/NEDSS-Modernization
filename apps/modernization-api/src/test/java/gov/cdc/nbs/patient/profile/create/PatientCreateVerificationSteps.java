package gov.cdc.nbs.patient.profile.create;

import gov.cdc.nbs.testing.support.Active;
import io.cucumber.java.en.Then;
import org.springframework.test.web.servlet.ResultActions;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class PatientCreateVerificationSteps {

  private final Active<ResultActions> response;

  PatientCreateVerificationSteps(final Active<ResultActions> response) {
    this.response = response;
  }

  @Then("a new patient is created")
  public void a_new_patient_is_created() throws Exception {
    this.response.active().andExpect(status().isCreated());
  }

  @Then("the new patient's identifier is returned")
  public void the_new_patient_identifier_is_returned() throws Exception {
    this.response.active()
        .andExpect(
            jsonPath("$.id")
                .exists()
        );
  }

  @Then("the new patient's local identifier is returned")
  public void the_new_patient_local_identifier_is_returned() throws Exception {
    this.response.active()
        .andExpect(
            jsonPath("$.local")
                .exists()
        );
  }

  @Then("the new patient's short identifier is returned")
  public void the_new_patient_short_identifier_is_returned() throws Exception {
    this.response.active()
        .andExpect(
            jsonPath("$.shortId")
                .exists()
        );
  }

  @Then("the new patient's name {string} {string} is returned")
  public void the_new_patient_name_is_returned(final String first, final String last) throws Exception {
    this.response.active()
        .andExpectAll(
            jsonPath("$.name.first").value(first),
            jsonPath("$.name.last").value(last)
        );
  }

}
