package gov.cdc.nbs.patient.file.demographics.mortality;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import gov.cdc.nbs.testing.support.Active;
import io.cucumber.java.en.Then;
import java.time.LocalDate;
import org.springframework.test.web.servlet.ResultActions;

public class PatientFileMortalityDemographicVerificationSteps {

  private final Active<ResultActions> response;

  PatientFileMortalityDemographicVerificationSteps(final Active<ResultActions> response) {
    this.response = response;
  }

  @Then("the patient file mortality demographics are as of {localDate}")
  public void hasAsOf(final LocalDate asOf) throws Exception {
    this.response.active().andExpect(jsonPath("$.asOf").value(asOf.toString()));
  }

  @Then("the patient file mortality demographics has the patient deceased on {localDate}")
  public void hasBornOn(final LocalDate asOf) throws Exception {
    this.response.active().andExpect(jsonPath("$.deceasedOn").value(asOf.toString()));
  }

  @Then("the patient file mortality demographics shows that the patient {indicator} deceased")
  public void deceased(final String value) throws Exception {
    this.response.active().andExpect(jsonPath("$.deceased.value").value(value));
  }

  @Then("the patient file mortality demographics has the patient dying in the city of {string}")
  public void hasCity(final String value) throws Exception {
    this.response.active().andExpect(jsonPath("$.city").value(value));
  }

  @Then("the patient file mortality demographics has the patient dying in the state of {state}")
  public void hasState(final String value) throws Exception {
    this.response.active().andExpect(jsonPath("$.state.value").value(value));
  }

  @Then("the patient file mortality demographics has the patient dying in the county of {county}")
  public void hasCounty(final String value) throws Exception {
    this.response.active().andExpect(jsonPath("$.county.value").value(value));
  }

  @Then("the patient file mortality demographics has the patient dying in the country of {country}")
  public void hasCountry(final String value) throws Exception {
    this.response.active().andExpect(jsonPath("$.country.value").value(value));
  }

  @Then("the patient file mortality demographics does not include details of death")
  public void notDeathDetails() throws Exception {
    this.response
        .active()
        .andExpect(jsonPath("$.deceasedOn").doesNotExist())
        .andExpect(jsonPath("$.city").doesNotExist())
        .andExpect(jsonPath("$.state").doesNotExist())
        .andExpect(jsonPath("$.county").doesNotExist())
        .andExpect(jsonPath("$.country").doesNotExist());
  }
}
