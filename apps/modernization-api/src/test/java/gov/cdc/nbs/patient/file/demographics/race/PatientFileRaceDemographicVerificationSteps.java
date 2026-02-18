package gov.cdc.nbs.patient.file.demographics.race;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import gov.cdc.nbs.testing.support.Active;
import io.cucumber.java.en.Then;
import java.time.LocalDate;
import org.springframework.test.web.servlet.ResultActions;

public class PatientFileRaceDemographicVerificationSteps {

  private final Active<ResultActions> response;

  PatientFileRaceDemographicVerificationSteps(final Active<ResultActions> response) {
    this.response = response;
  }

  @Then("the patient file race demographics includes the category {raceCategory}")
  public void hasCategory(final String category) throws Exception {
    this.response.active().andExpect(jsonPath("$.[?(@.race.value=='%s')]", category).exists());
  }

  @Then("the patient file race demographics does not include the category {raceCategory}")
  public void doesNotHaveCategory(final String category) throws Exception {
    this.response.active().andExpect(jsonPath("$.[?(@.race.value=='%s')]", category).isEmpty());
  }

  @Then("the patient file race demographics as of {localDate} includes the category {raceCategory}")
  public void includesCategory(final LocalDate asOf, final String category) throws Exception {
    this.response
        .active()
        .andExpect(jsonPath("$.[?(@.asOf=='%s' && @.race.value=='%s')]", asOf, category).exists());
  }

  @Then("the {nth} patient file race demographic is as of {localDate}")
  public void nthAsOf(final int position, final LocalDate value) throws Exception {
    this.response.active().andExpect(jsonPath("$[%s].asOf", position - 1).value(value.toString()));
  }

  @Then("the {nth} patient file race demographic has the category {raceCategory}")
  public void nthCategory(final int position, final String value) throws Exception {
    this.response.active().andExpect(jsonPath("$[%s].race.value", position - 1).value(value));
  }

  @Then("the patient file race demographics includes {raceDetail} within the {raceCategory} race")
  public void categoryIncludesDetail(final String detail, final String category) throws Exception {
    this.response
        .active()
        .andExpect(
            jsonPath("$.[?(@.race.value=='%s')].detailed[?(@.value=='%s')]", category, detail)
                .exists());
  }

  @Then(
      "the patient file race demographics does not include {raceDetail} within the category {raceCategory}")
  public void categoryDoesNotIncludeDetail(final String detail, final String category)
      throws Exception {
    this.response
        .active()
        .andExpect(
            jsonPath("$.[?(@.race.value=='%s')].detailed[?(@.value=='%s')]", category, detail)
                .doesNotExist());
  }

  @Then("the patient file race demographics does not include details for the {raceCategory} race")
  public void categoryDoesNotIncludeDetails(final String category) throws Exception {
    this.response
        .active()
        .andExpect(jsonPath("$.[?(@.race.value=='%s')].detailed[*]", category).isEmpty());
  }
}
