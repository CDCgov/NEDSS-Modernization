package gov.cdc.nbs.patient.file.demographics.birth;

import gov.cdc.nbs.testing.support.Active;
import io.cucumber.java.en.Then;
import org.springframework.test.web.servlet.ResultActions;

import java.time.LocalDate;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

public class PatientFileBirthDemographicVerificationSteps {

  private final Active<ResultActions> response;

  PatientFileBirthDemographicVerificationSteps(
      final Active<ResultActions> response
  ) {
    this.response = response;
  }

  @Then("the patient file birth demographics are as of {localDate}")
  public void hasAsOf(final LocalDate asOf) throws Exception {
    this.response.active()
        .andExpect(jsonPath("$.asOf").value(asOf.toString()));
  }

  @Then("the patient file birth demographics has the birth date of {localDate}")
  public void hasBornOn(final LocalDate asOf) throws Exception {
    this.response.active()
        .andExpect(jsonPath("$.bornOn").value(asOf.toString()));
  }

  @Then("the patient file birth demographics has the birth sex of {sex}")
  public void hasSex(final String value) throws Exception {
    this.response.active()
        .andExpect(jsonPath("$.sex.value").value(value));
  }

  @Then("the patient file birth demographics has the patient multiple as {indicator}")
  public void hasMultiple(final String value) throws Exception {
    this.response.active()
        .andExpect(jsonPath("$.multiple.value").value(value));
  }

  @Then("the patient file birth demographics has the patient born {nth}")
  public void hasOrder(final int value) throws Exception {
    this.response.active()
        .andExpect(jsonPath("$.order").value(value));
  }

  @Then("the patient file birth demographics has the patient born in the city of {string}")
  public void hasCity(final String value) throws Exception {
    this.response.active()
        .andExpect(jsonPath("$.city").value(value));
  }

  @Then("the patient file birth demographics has the patient born in the state of {state}")
  public void hasState(final String value) throws Exception {
    this.response.active()
        .andExpect(jsonPath("$.state.value").value(value));
  }

  @Then("the patient file birth demographics has the patient born in the county of {county}")
  public void hasCounty(final String value) throws Exception {
    this.response.active()
        .andExpect(jsonPath("$.county.value").value(value));
  }

  @Then("the patient file birth demographics has the patient born in the country of {country}")
  public void hasCountry(final String value) throws Exception {
    this.response.active()
        .andExpect(jsonPath("$.country.value").value(value));
  }

}
