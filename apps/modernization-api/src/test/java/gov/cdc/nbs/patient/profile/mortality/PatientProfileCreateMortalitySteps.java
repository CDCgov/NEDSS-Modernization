package gov.cdc.nbs.patient.profile.mortality;

import gov.cdc.nbs.patient.identifier.PatientIdentifier;
import gov.cdc.nbs.testing.support.Active;
import io.cucumber.java.en.Then;
import org.springframework.test.web.servlet.ResultActions;

import java.time.LocalDate;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

public class PatientProfileCreateMortalitySteps {

  private final Active<PatientIdentifier> activePatient;
  private final PatientProfileMortalityRequester requester;
  private final Active<ResultActions> response;


  PatientProfileCreateMortalitySteps(
      final Active<PatientIdentifier> activePatient,
      final PatientProfileMortalityRequester requester,
      final Active<ResultActions> response) {
    this.activePatient = activePatient;
    this.requester = requester;
    this.response = response;
  }

  @Then("I view the Patient Profile Mortality demographics")
  public void i_view_the_patient_profile_mortality_demographics() {
    this.activePatient.maybeActive()
        .map(requester::mortality)
        .ifPresent(this.response::active);
  }

  @Then("the patient profile mortality has the as of date {localDate}")
  public void the_patient_profile_mortality_has_an_as_of_date_of(final LocalDate value) throws Exception {
    this.response.active()
        .andExpect(
            jsonPath("$.data.findPatientProfile.mortality.asOf")
                .value(value.toString()));
  }

  @Then("the patient profile mortality has the deceased on date {localDate}")
  public void the_patient_profile_mortality_has_the_deceased_on_date_of(final LocalDate value) throws Exception {
    this.response.active()
        .andExpect(
            jsonPath("$.data.findPatientProfile.mortality.deceasedOn")
                .value(value.toString()));
  }


  @Then("the patient profile mortality has the city {string}")
  public void the_patient_profile_mortality_has_the_city(final String value) throws Exception {
    this.response.active()
        .andExpect(
            jsonPath("$.data.findPatientProfile.mortality.city")
                .value(value));
  }

  @Then("the patient profile mortality has the county {county}")
  public void the_patient_profile_mortality_has_the_county(final String value) throws Exception {
    this.response.active()
        .andExpect(
            jsonPath("$.data.findPatientProfile.mortality.county.id")
                .value(value));
  }

  @Then("the patient profile mortality has the state {state}")
  public void the_patient_profile_mortality_has_the_state(final String value) throws Exception {
    this.response.active()
        .andExpect(
            jsonPath("$.data.findPatientProfile.mortality.state.id")
                .value(value));
  }


  @Then("the patient profile mortality has the country {country}")
  public void the_patient_profile_mortality_has_the_country(final String value) throws Exception {
    this.response.active()
        .andExpect(
            jsonPath("$.data.findPatientProfile.mortality.country.id")
                .value(value));
  }

  @Then("the patient profile mortality has the deceased option as {indicator}")
  public void the_patient_profile_mortality_has_the_deceased_option_as(final String value) throws Exception {
    this.response.active()
        .andExpect(
            jsonPath("$.data.findPatientProfile.mortality.deceased.id")
                .value(value));
  }

}
