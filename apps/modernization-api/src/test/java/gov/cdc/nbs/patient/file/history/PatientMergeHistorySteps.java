package gov.cdc.nbs.patient.file.history;
import gov.cdc.nbs.testing.support.Active;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.springframework.test.web.servlet.ResultActions;

public class PatientMergeHistorySteps {

  private final Active<ResultActions> response;
  private final PatientMergeHistoryRequester requester;

  public PatientMergeHistorySteps(
      final Active<ResultActions> response,
      final PatientMergeHistoryRequester requester
  ) {
    this.response = response;
    this.requester = requester;
  }

  @When("I view the merge history for patient {int}")
  public void i_view_the_merge_history_for(final long patient) {
    this.response.active(requester.request(patient));
  }

  @Then("the request succeeds")
  public void the_request_succeeds() throws Exception {
    this.response.active()
        .andExpect(org.springframework.test.web.servlet.result.MockMvcResultMatchers.status().isOk());
  }
}
