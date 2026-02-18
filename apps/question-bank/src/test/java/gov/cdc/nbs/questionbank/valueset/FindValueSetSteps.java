package gov.cdc.nbs.questionbank.valueset;

import static org.hamcrest.Matchers.equalTo;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import gov.cdc.nbs.testing.support.Active;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.springframework.test.web.servlet.ResultActions;

public class FindValueSetSteps {

  private final Active<ResultActions> response;
  private final FindValueSetRequester requester;

  public FindValueSetSteps(
      final Active<ResultActions> response, final FindValueSetRequester requester) {
    this.response = response;
    this.requester = requester;
  }

  @When("I find the value set {string}")
  public void find_value_set(String valueset) throws Exception {
    response.active(requester.send(valueset));
  }

  @Then(
      "the returned value set has type {string}, code {string}, name {string}, and description {string}")
  public void value_set_is_returned(
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
