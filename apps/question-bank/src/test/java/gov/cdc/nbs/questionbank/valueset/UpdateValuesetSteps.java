package gov.cdc.nbs.questionbank.valueset;

import static org.hamcrest.Matchers.equalTo;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import gov.cdc.nbs.questionbank.valueset.request.UpdateValueSetRequest;
import gov.cdc.nbs.testing.support.Active;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.springframework.test.web.servlet.ResultActions;

public class UpdateValuesetSteps {

  private final Active<ResultActions> response;
  private final UpdateValuesetRequester requester;

  private UpdateValueSetRequest request;

  public UpdateValuesetSteps(
      final Active<ResultActions> response, final UpdateValuesetRequester requester) {
    this.response = response;
    this.requester = requester;
  }

  @Given("I have a request to update a value set with name {string} and description {string}")
  public void update_request(String name, String description) {
    request = new UpdateValueSetRequest(name, description);
  }

  @When("I send a request to update the value set {string}")
  public void send_update_request(String valueset) throws Exception {
    response.active(requester.send(valueset, request));
  }

  @Then("the value set is updated")
  public void value_set_is_updated() throws Exception {
    response
        .active()
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.name", equalTo(request.name())))
        .andExpect(jsonPath("$.description", equalTo(request.description())));
  }
}
