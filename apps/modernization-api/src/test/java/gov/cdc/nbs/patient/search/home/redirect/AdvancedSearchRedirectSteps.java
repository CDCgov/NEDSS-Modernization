package gov.cdc.nbs.patient.search.home.redirect;

import static org.hamcrest.CoreMatchers.startsWith;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import gov.cdc.nbs.testing.authorization.ActiveUser;
import gov.cdc.nbs.testing.support.Active;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.springframework.test.web.servlet.ResultActions;

public class AdvancedSearchRedirectSteps {

  private final Active<ActiveUser> activeUser;
  private final Active<ResultActions> response;

  private final AdvancedSearchRedirectRequester requester;

  AdvancedSearchRedirectSteps(
      final Active<ActiveUser> activeUser,
      final AdvancedSearchRedirectRequester requester,
      final Active<ResultActions> response) {
    this.activeUser = activeUser;
    this.requester = requester;
    this.response = response;
  }

  @When("I click Advanced Search on the NBS Home page")
  public void i_navigate_to_the_NBS_advanced_search_page() {
    response.active(requester.request());
  }

  @Then("I am redirected to Advanced Search")
  public void i_am_redirected_to_advanced_search() throws Exception {
    this.response
        .active()
        .andExpect(status().isFound())
        .andExpect(header().string("Location", startsWith("/search")));
  }

  @Then("the user Id is present in the redirect")
  public void the_user_id_is_present_in_the_redirect() throws Exception {
    response.active().andExpect(cookie().value("nbs_user", activeUser.active().username()));
  }

  @Then("the token is present in the redirect")
  public void the_token_is_present_in_the_redirect() throws Exception {
    response.active().andExpect(cookie().exists("nbs_token"));
  }
}
