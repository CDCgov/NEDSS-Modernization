package gov.cdc.nbs.patient.profile.gender;

import gov.cdc.nbs.testing.support.Active;
import io.cucumber.java.en.Then;
import org.springframework.test.web.servlet.ResultActions;

import java.time.Instant;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

public class PatientProfileGenderDemographicVerificationSteps {

  private final Active<ResultActions> response;

  PatientProfileGenderDemographicVerificationSteps(final Active<ResultActions> response) {
    this.response = response;
  }

  @Then("the patient profile gender demographics has the as of date {date}")
  public void the_patient_profile_gender_has_an_as_of_date_of(final Instant value) throws Exception {
    this.response.active()
        .andExpect(
            jsonPath("$.data.findPatientProfile.gender.asOf")
                .value(value.toString())
        );
  }

  @Then("the patient profile gender demographics has the current gender as {sex}")
  public void the_patient_profile_gender_has_the_current_gender(final String value) throws Exception {
    this.response.active()
        .andExpect(
            jsonPath("$.data.findPatientProfile.gender.current.id")
                .value(value)
        );
  }

  @Then("the patient profile gender demographics has the unknown reason {sexUnknown}")
  public void the_patient_profile_gender_has_unknown_reason(final String value) throws Exception {
    this.response.active()
        .andExpect(
            jsonPath("$.data.findPatientProfile.gender.unknownReason.id")
                .value(value)
        );
  }

  @Then("the patient profile gender demographics has the preferred gender {transgender}")
  public void the_patient_profile_gender_has_preferred(final String value) throws Exception {
    this.response.active()
        .andExpect(
            jsonPath("$.data.findPatientProfile.gender.preferred.id")
                .value(value)
        );
  }

  @Then("the patient profile gender demographics has the additional gender {string}")
  public void the_patient_profile_gender_has_additional_gender(final String value) throws Exception {
    this.response.active()
        .andExpect(
            jsonPath("$.data.findPatientProfile.gender.additional")
                .value(value)
        );
  }
}
