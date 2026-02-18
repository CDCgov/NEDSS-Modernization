package gov.cdc.nbs.patient.file.demographics.administrative;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import gov.cdc.nbs.testing.support.Active;
import io.cucumber.java.en.Then;
import java.time.LocalDate;
import org.springframework.test.web.servlet.ResultActions;

public class PatientFileAdministrativeInformationVerificationSteps {

  private final Active<ResultActions> response;

  PatientFileAdministrativeInformationVerificationSteps(final Active<ResultActions> response) {
    this.response = response;
  }

  @Then("the patient file administrative information has the as of date {localDate}")
  public void hasAsOf(final LocalDate value) throws Exception {
    this.response.active().andExpect(jsonPath("$.asOf").value(value.toString()));
  }

  @Then("the patient file administrative information has the comment {string}")
  public void hasComment(final String value) throws Exception {
    this.response.active().andExpect(jsonPath("$.comment").value(value));
  }
}
