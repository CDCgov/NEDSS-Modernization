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

  @Then("the patient file merge history has an entry with superseded person local ID {string}")
  public void hasSupersededPersonLocalId(final String localId) throws Exception {
    this.response.active()
        .andExpect(
            jsonPath("$[0].supersededPersonLocalId").value(localId)
        );
  }

  @Then("the patient file merge history has an entry with legal name {string}")
  public void hasLegalName(final String name) throws Exception {
    this.response.active()
        .andExpect(
            jsonPath("$[0].supersededPersonLegalName").value(name)
        );
  }

  @Then("the patient file merge history has an entry with merge timestamp {string}")
  public void hasMergeTimestamp(final String timestamp) throws Exception {
    this.response.active()
        .andExpect(
            jsonPath("$[0].mergeTimestamp").value(timestamp)
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
