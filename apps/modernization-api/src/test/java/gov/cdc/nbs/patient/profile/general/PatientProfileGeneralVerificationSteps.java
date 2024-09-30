package gov.cdc.nbs.patient.profile.general;

import gov.cdc.nbs.testing.support.Active;
import io.cucumber.java.en.Then;
import org.springframework.test.web.servlet.ResultActions;

import java.time.Instant;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.equalToIgnoringCase;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;


public class PatientProfileGeneralVerificationSteps {

  private final Active<ResultActions> response;

  PatientProfileGeneralVerificationSteps(final Active<ResultActions> response) {
    this.response = response;
  }

  @Then("the patient('s) general information is as of {date}")
  public void the_patients_general_information_includes_the_as_of(final Instant value) throws Exception {
    this.response.active()
        .andExpect(
            jsonPath("$.data.findPatientProfile.general.asOf", equalTo(value.toString()))
        );
  }

  @Then("the patient('s) general information includes the marital status {maritalStatus}")
  public void the_patients_general_information_includes_the_marital_status(final String value) throws Exception {
    this.response.active()
        .andExpect(
            jsonPath("$.data.findPatientProfile.general.maritalStatus.id", equalTo(value))
        );
  }

  @Then("the patient('s) general information includes the occupation {occupation}")
  public void the_patients_general_information_includes_the_occupation(final String value) throws Exception {
    this.response.active()
        .andExpect(
            jsonPath("$.data.findPatientProfile.general.occupation.id", equalTo(value))
        );
  }

  @Then("the patient('s) general information includes an education level of {educationLevel}")
  public void the_patients_general_information_includes_the_education_level(final String value) throws Exception {
    this.response.active()
        .andExpect(
            jsonPath("$.data.findPatientProfile.general.educationLevel.id", equalTo(value))
        );
  }

  @Then("the patient('s) general information includes a primary language of {language}")
  public void the_patients_general_information_includes_the_primary_language(final String value) throws Exception {
    this.response.active()
        .andExpect(
            jsonPath("$.data.findPatientProfile.general.primaryLanguage.id", equalTo(value))
        );
  }

  @Then("the patient('s) general information includes that the patient {indicator} speak English")
  public void the_patients_general_information_includes_speaks_english(final String value) throws Exception {
    this.response.active()
        .andExpect(
            jsonPath("$.data.findPatientProfile.general.speaksEnglish.id", equalTo(value))
        );
  }

  @Then("the patient('s) general information includes a mother's maiden name of {string}")
  public void the_patients_general_information_includes_maternal_maiden_name(final String value)
      throws Exception {
    this.response.active()
        .andExpect(jsonPath("$.data.findPatientProfile.general.maternalMaidenName").value(equalTo(value)));
  }

  @Then("the patient('s) general information does not include a mother's maiden name of")
  public void the_patients_general_information_does_not_include()
      throws Exception {
    this.response.active()
        .andExpect(jsonPath("$.data.findPatientProfile.general.maternalMaidenName").isEmpty());
  }

  @Then("the patient('s) general information includes {int} adults in the house")
  public void the_patients_general_information_includes_adults_in_residence(final int value)
      throws Exception {
    this.response.active()
        .andExpect(jsonPath("$.data.findPatientProfile.general.adultsInHouse").value(equalTo(value)));
  }

  @Then("the patient('s) general information does not include adults in the house")
  public void the_patients_general_information_does_not_include_adults_in_residence()
      throws Exception {
    this.response.active()
        .andExpect(jsonPath("$.data.findPatientProfile.general.adultsInHouse").isEmpty());
  }

  @Then("the patient('s) general information includes {int} children in the house")
  public void the_patients_general_information_includes_children_in_residence(final int value)
      throws Exception {
    this.response.active()
        .andExpect(jsonPath("$.data.findPatientProfile.general.childrenInHouse").value(equalTo(value)));
  }

  @Then("the patient('s) general information does not include children in the house")
  public void the_patients_general_information_does_not_include_children_in_residence()
      throws Exception {
    this.response.active()
        .andExpect(jsonPath("$.data.findPatientProfile.general.childrenInHouse").isEmpty());
  }

  @Then("the patient('s) general information includes a state HIV case of {string}")
  public void the_patients_general_information_includes_a_state_HIV_case(final String value) throws Exception {
    this.response.active()
        .andExpect(jsonPath("$.data.findPatientProfile.general.stateHIVCase.value").value(equalTo(value)));
  }

  @Then("the patient('s) general information does not include a state HIV case")
  public void the_patients_general_information_does_not_include_a_state_HIV_case() throws Exception {
    this.response.active()
        .andExpect(jsonPath("$.data.findPatientProfile.general.stateHIVCase.value").isEmpty());
  }

  @Then("the patient's general information does not include state HIV case due to {string}")
  public void the_patients_general_information_does_not_include_state_HIV_case(final String reason) throws Exception {
    this.response.active()
        .andExpect(jsonPath("$.data.findPatientProfile.general.stateHIVCase.reason", equalToIgnoringCase(reason)));
  }
}
