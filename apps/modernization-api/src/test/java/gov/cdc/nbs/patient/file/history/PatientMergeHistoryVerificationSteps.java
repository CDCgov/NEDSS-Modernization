package gov.cdc.nbs.patient.file.history;

import gov.cdc.nbs.testing.support.Active;
import io.cucumber.java.en.Then;
import org.springframework.test.web.servlet.ResultActions;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

public class PatientMergeHistoryVerificationSteps {

  private final Active<ResultActions> response;

  PatientMergeHistoryVerificationSteps(final Active<ResultActions> response) {
    this.response = response;
  }


  @Then("the patient file merge history has an entry with legal name {string}")
  public void hasLegalName(final String name) throws Exception {
    this.response.active()
        .andExpect(
            jsonPath("$[0].supersededPersonLegalName").value(name)
        );
  }


  @Then("the patient file merge history has an entry with merged by user {string}")
  public void hasMergedByUser(final String user) throws Exception {
    this.response.active()
        .andExpect(
            jsonPath("$[0].mergedByUser").value(user)
        );
  }
}
