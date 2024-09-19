package gov.cdc.nbs.patient.profile.administrative;

import gov.cdc.nbs.testing.support.Active;
import io.cucumber.java.en.Then;
import org.springframework.test.web.servlet.ResultActions;

import java.time.Instant;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

public class PatientProfileAdministrativeVerificationSteps {

  private final Active<ResultActions> response;

  PatientProfileAdministrativeVerificationSteps(final Active<ResultActions> response) {
    this.response = response;
  }

  @Then("the patient profile administrative has the as of date {date}")
  public void the_patient_profile_administrative_has_an_as_of_date_of(final Instant value) throws Exception {
    this.response.active()
        .andExpect(
            jsonPath("$.data.findPatientProfile.administrative.content[*].asOf")
                .value(value.toString())
        );
  }

  @Then("the patient profile administrative has the comment {string}")
  public void the_patient_profile_administrative_has_the_comment(final String value) throws Exception {
    this.response.active()
        .andExpect(
            jsonPath("$.data.findPatientProfile.administrative.content[*].comment")
                .value(value)
        );
  }
}
