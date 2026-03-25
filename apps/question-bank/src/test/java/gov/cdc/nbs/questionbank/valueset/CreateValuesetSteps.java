package gov.cdc.nbs.questionbank.valueset;

import static org.hamcrest.Matchers.equalTo;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import gov.cdc.nbs.questionbank.valueset.request.CreateValuesetRequest;
import gov.cdc.nbs.testing.support.Active;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.springframework.test.web.servlet.ResultActions;

public class CreateValuesetSteps {

  private final CreateValuesetRequester requester;
  private final Active<ResultActions> response;

  public CreateValuesetSteps(
      final CreateValuesetRequester requester, final Active<ResultActions> response) {
    this.requester = requester;
    this.response = response;
  }

  @When(
      "I send a request to create a valueset with type {string}, code {string}, name {string}, and description {string}")
  public void i_send_create_valueset_request(
      final String type, final String code, final String name, final String description)
      throws Exception {
    response.active(requester.create(new CreateValuesetRequest(type, code, name, description)));
  }

  @Then(
      "the valueset is created with type {string}, code {string}, name {string}, and description {string}")
  public void the_valueset_is_created(
      final String type, final String code, final String name, final String description)
      throws Exception {
    response
        .active()
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.type", equalTo(type)))
        .andExpect(jsonPath("$.code", equalTo(code.toUpperCase())))
        .andExpect(jsonPath("$.name", equalTo(name)))
        .andExpect(jsonPath("$.description", equalTo(description)));
  }
}
