package gov.cdc.nbs.patient.file.demographics.general;

import gov.cdc.nbs.testing.support.Active;
import io.cucumber.java.en.Then;
import org.springframework.test.web.servlet.ResultActions;

import java.time.LocalDate;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.equalToIgnoringCase;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;


public class PatientFileGeneralInformationVerificationSteps {

  private final Active<ResultActions> response;

  PatientFileGeneralInformationVerificationSteps(final Active<ResultActions> response) {
    this.response = response;
  }

  @Then("the patient file general information is as of {localDate}")
  public void asOf(final LocalDate value) throws Exception {
    this.response.active()
        .andExpect(
            jsonPath("$.asOf", equalTo(value.toString()))
        );
  }

  @Then("the patient file general information includes the marital status {maritalStatus}")
  public void maritalStatus(final String value) throws Exception {
    this.response.active()
        .andExpect(
            jsonPath("$.maritalStatus.value", equalTo(value))
        );
  }

  @Then("the patient file general information includes the occupation {occupation}")
  public void occupation(final String value) throws Exception {
    this.response.active()
        .andExpect(
            jsonPath("$.occupation.value", equalTo(value))
        );
  }

  @Then("the patient file general information includes an education level of {educationLevel}")
  public void education(final String value) throws Exception {
    this.response.active()
        .andExpect(
            jsonPath("$.educationLevel.value", equalTo(value))
        );
  }

  @Then("the patient file general information includes a primary language of {language}")
  public void language(final String value) throws Exception {
    this.response.active()
        .andExpect(
            jsonPath("$.primaryLanguage.value", equalTo(value))
        );
  }

  @Then("the patient file general information includes that the patient {indicator} speak English")
  public void speaksEnglish(final String value) throws Exception {
    this.response.active()
        .andExpect(
            jsonPath("$.speaksEnglish.value", equalTo(value))
        );
  }

  @Then("the patient file general information includes a mother's maiden name of {string}")
  public void maidenName(final String value)
      throws Exception {
    this.response.active()
        .andExpect(jsonPath("$.maternalMaidenName").value(equalTo(value)));
  }

  @Then("the patient file general information does not include a mother's maiden name of")
  public void noMaidenName()
      throws Exception {
    this.response.active()
        .andExpect(jsonPath("$.maternalMaidenName").isEmpty());
  }

  @Then("the patient file general information includes {int} adults in the house")
  public void adults(final int value)
      throws Exception {
    this.response.active()
        .andExpect(jsonPath("$.adultsInHouse").value(equalTo(value)));
  }

  @Then("the patient file general information does not include adults in the house")
  public void noAdults()
      throws Exception {
    this.response.active()
        .andExpect(jsonPath("$.adultsInHouse").isEmpty());
  }

  @Then("the patient file general information includes {int} children in the house")
  public void children(final int value)
      throws Exception {
    this.response.active()
        .andExpect(jsonPath("$.childrenInHouse").value(equalTo(value)));
  }

  @Then("the patient file general information does not include children in the house")
  public void noChildren()
      throws Exception {
    this.response.active()
        .andExpect(jsonPath("$.childrenInHouse").isEmpty());
  }

  @Then("the patient file general information includes a state HIV case of {string}")
  public void stateHIVCase(final String value) throws Exception {
    this.response.active()
        .andExpect(jsonPath("$.stateHIVCase.value").value(equalTo(value)));
  }

  @Then("the patient file general information does not include a state HIV case")
  public void noStateHIVCase() throws Exception {
    this.response.active()
        .andExpect(jsonPath("$.stateHIVCase.value").isEmpty());
  }

  @Then("the patient's general information does not include state HIV case due to {string}")
  public void noStateHIVCase(final String reason) throws Exception {
    this.response.active()
        .andExpect(jsonPath("$.stateHIVCase.reason", equalToIgnoringCase(reason)));
  }
}
