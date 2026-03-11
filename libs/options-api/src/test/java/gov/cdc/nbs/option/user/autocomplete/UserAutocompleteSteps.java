package gov.cdc.nbs.option.user.autocomplete;

import gov.cdc.nbs.testing.support.Active;
import io.cucumber.java.Before;
import io.cucumber.java.en.When;
import org.springframework.test.web.servlet.ResultActions;

public class UserAutocompleteSteps {

  private final UserAutocompleteRequester request;
  private final Active<ResultActions> response;

  UserAutocompleteSteps(
      final UserAutocompleteRequester request, final Active<ResultActions> response) {
    this.request = request;
    this.response = response;
  }

  @Before("@users")
  public void reset() {
    response.reset();
  }

  @When("I am trying to find users that start with {string}")
  public void i_am_trying_to_find_users_that_start_with(final String criteria) throws Exception {
    response.active(request.complete(criteria));
  }

  @When("I am trying to find at most {int} users(s) that start with {string}")
  public void i_am_trying_to_find_n_users_that_start_with(final int limit, final String criteria)
      throws Exception {
    response.active(request.complete(criteria, limit));
  }
}
