package gov.cdc.nbs.patient.file.demographics.gender;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import gov.cdc.nbs.testing.support.Active;
import io.cucumber.java.en.Then;
import java.time.LocalDate;
import org.springframework.test.web.servlet.ResultActions;

public class PatientFileGenderDemographicVerificationSteps {

  private final Active<ResultActions> response;

  PatientFileGenderDemographicVerificationSteps(Active<ResultActions> response) {
    this.response = response;
  }

  @Then("the patient file gender demographics are as of {localDate}")
  public void hasAsOf(final LocalDate asOf) throws Exception {
    this.response.active().andExpect(jsonPath("$.asOf").value(asOf.toString()));
  }

  @Then("the patient file gender demographics has the current gender of {sex}")
  public void hasCurrent(final String value) throws Exception {
    this.response.active().andExpect(jsonPath("$.current.value").value(value));
  }

  @Then("the patient file gender demographics has the transgender of {transgender}")
  @Then("the patient file gender demographics has a preferred gender of {transgender}")
  public void hasTransgender(final String value) throws Exception {
    this.response.active().andExpect(jsonPath("$.transgenderInformation.value").value(value));
  }

  @Then("the patient file gender demographics has the {string} additional gender")
  public void hasAdditional(final String value) throws Exception {
    this.response.active().andExpect(jsonPath("$.additionalGender").value(value));
  }

  @Then(
      "the patient file gender demographics has an unknown current gender with the reason being {sexUnknown}")
  public void hasUnknownReason(final String reason) throws Exception {
    this.response
        .active()
        .andExpect(jsonPath("$.current.value").value("U"))
        .andExpect(jsonPath("$.unknownReason.value").value(reason));
  }
}
