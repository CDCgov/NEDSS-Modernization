package gov.cdc.nbs.patient.profile.birth;

import gov.cdc.nbs.testing.support.Active;
import io.cucumber.java.en.Then;
import org.springframework.test.web.servlet.ResultActions;

import java.time.Instant;
import java.time.LocalDate;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

public class PatientProfileBirthDemographicVerificationSteps {

  private final Active<ResultActions> response;

  PatientProfileBirthDemographicVerificationSteps(final Active<ResultActions> response) {
    this.response = response;
  }

  @Then("the patient profile birth demographics has the as of date {date}")
  public void the_patient_profile_birth_has_an_as_of_date_of(final Instant value) throws Exception {
    this.response.active()
        .andExpect(
            jsonPath("$.data.findPatientProfile.birth.asOf")
                .value(value.toString())
        );
  }

  @Then("the patient profile birth demographics has the patient born on {localDate}")
  public void the_patient_profile_birth_has_the_patient_born_on(final LocalDate value) throws Exception {
    this.response.active()
        .andExpect(
            jsonPath("$.data.findPatientProfile.birth.bornOn")
                .value(value.toString())
        );
  }

  @Then("the patient profile birth demographics has patient born as {sex}")
  public void the_patient_profile_birth_has_the_birth_sex_of(final String value) throws Exception {
    this.response.active()
        .andExpect(
            jsonPath("$.data.findPatientProfile.gender.birth.id")
                .value(value)
        );
  }

  @Then("the patient profile birth demographics has {indicator} for multiple birth")
  public void the_patient_profile_birth_has_the_value_for_multiple_birth(final String value) throws Exception {
    this.response.active()
        .andExpect(
            jsonPath("$.data.findPatientProfile.birth.multipleBirth.id")
                .value(value)
        );
  }

  @Then("the patient profile birth demographics has the patient born {nth}")
  public void the_patient_profile_birth_has_the_patient_born(final int value) throws Exception {
    this.response.active()
        .andExpect(
            jsonPath("$.data.findPatientProfile.birth.birthOrder")
                .value(value)
        );
  }

  @Then("the patient profile birth demographics has the patient born in the city of {string}")
  public void the_patient_profile_birth_has_the_patient_born_in_the_city(final String value) throws Exception {
    this.response.active()
        .andExpect(
            jsonPath("$.data.findPatientProfile.birth.city")
                .value(value)
        );
  }

  @Then("the patient profile birth demographics has the patient born in the county of {county}")
  public void the_patient_profile_birth_has_the_patient_born_in_the_county(final String value) throws Exception {
    this.response.active()
        .andExpect(
            jsonPath("$.data.findPatientProfile.birth.county.id")
                .value(value)
        );
  }

  @Then("the patient profile birth demographics has the patient born in the state of {state}")
  public void the_patient_profile_birth_has_the_patient_born_in_the_state(final String value) throws Exception {
    this.response.active()
        .andExpect(
            jsonPath("$.data.findPatientProfile.birth.state.id")
                .value(value)
        );
  }


  @Then("the patient profile birth demographics has the patient born in the country of {country}")
  public void the_patient_profile_birth_has_the_patient_born_in_the_country(final String value) throws Exception {
    this.response.active()
        .andExpect(
            jsonPath("$.data.findPatientProfile.birth.country.id")
                .value(value)
        );
  }
}
