package gov.cdc.nbs.authentication.login;

import static org.hamcrest.Matchers.equalTo;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import gov.cdc.nbs.testing.support.Active;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.springframework.test.web.servlet.ResultActions;

public class LoginSteps {

  private final LoginRequester requester;
  private final Active<ResultActions> response;

  LoginSteps(final LoginRequester requester, final Active<ResultActions> response) {
    this.requester = requester;
    this.response = response;
  }

  @When("I log in as {string}")
  public void i_log_in_as(final String username) {
    this.response.active(this.requester.login(username));
  }

  @Then("the logged in user has the username {string}")
  public void the_logged_in_user_has_the_username(final String username) throws Exception {
    this.response.active().andExpect(jsonPath("$.username").value(equalTo(username)));
  }

  @Then("the logged in user has a token")
  public void the_logged_in_user_has_a_token() throws Exception {
    this.response.active().andExpect(jsonPath("$.token").isString());
  }
}
