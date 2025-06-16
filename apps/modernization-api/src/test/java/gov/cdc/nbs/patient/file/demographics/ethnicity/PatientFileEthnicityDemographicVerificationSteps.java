package gov.cdc.nbs.patient.file.demographics.ethnicity;

import gov.cdc.nbs.testing.support.Active;
import io.cucumber.java.en.Then;
import org.springframework.test.web.servlet.ResultActions;

import java.time.LocalDate;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

public class PatientFileEthnicityDemographicVerificationSteps {

  private final Active<ResultActions> response;

  PatientFileEthnicityDemographicVerificationSteps(
      final Active<ResultActions> response
  ) {
    this.response = response;
  }

  @Then("the patient file ethnicity demographics is as of {localDate}")
  public void hasAsOf(final LocalDate asOf) throws Exception {
    this.response.active()
        .andExpect(jsonPath("$.asOf").value(asOf.toString()));
  }

  @Then("the patient file ethnicity demographics has the ethnicity {ethnicity}")
  public void hasEthnicity(final String ethnicity) throws Exception {
    this.response.active()
        .andExpect(jsonPath("$.ethnicGroup.value").value(ethnicity));
  }

  @Then("the patient file ethnicity demographics is unknown with the reason being {ethnicityUnknownReason}")
  public void hasUnknownReason(final String reason) throws Exception {
    this.response.active()
        .andExpect(jsonPath("$.ethnicGroup.value").value("UNK"))
        .andExpect(jsonPath("$.unknownReason.value").value(reason));
  }


  @Then("the patient file ethnicity demographics includes the Spanish origin {ethnicityDetail}")
  @Then("the patient file ethnicity demographics includes the detail {ethnicityDetail}")
  public void includesDetail(final String detail) throws Exception {
    this.response.active()
        .andExpect(
            jsonPath(
                "$.detailed[?(@.value=='%s')]",
                detail
            ).exists()
        );
  }

  @Then("the patient file ethnicity demographics does not include the Spanish origin {ethnicityDetail}")
  @Then("the patient file ethnicity demographics does not include the detail {ethnicityDetail}")
  public void doestNotIncludeDetail(final String detail)
      throws Exception {
    this.response.active()
        .andExpect(
            jsonPath(
                "$.detailed[?(@.value=='%s')]",
                detail
            ).doesNotExist()
        );
  }

  @Then("the patient file ethnicity demographics does not include Spanish origins")
  @Then("the patient file ethnicity demographics does not include details")
  public void doesNotIncludeDetails()
      throws Exception {
    this.response.active()
        .andExpect(jsonPath("$.detailed[*]").isEmpty());
  }
}
