package gov.cdc.nbs.option.condition.autocomplete;

import gov.cdc.nbs.testing.support.Active;
import io.cucumber.java.Before;
import io.cucumber.java.en.When;
import org.springframework.test.web.servlet.ResultActions;

public class ConditionAutocompleteSteps {

  private final ConditionAutocompleteRequester request;
  private final Active<ResultActions> response;

  ConditionAutocompleteSteps(
      final ConditionAutocompleteRequester request, final Active<ResultActions> response) {
    this.request = request;
    this.response = response;
  }

  @Before("@condition")
  public void reset() {
    response.reset();
  }

  @When("I am trying to find conditions that start with {string}")
  public void i_am_trying_to_find_conditions_that_start_with(final String criteria)
      throws Exception {
    response.active(request.complete(criteria));
  }

  @When("I am trying to find at most {int} condition(s) that start with {string}")
  public void i_am_trying_to_find_n_conditions_that_start_with(
      final int limit, final String criteria) throws Exception {
    response.active(request.complete(criteria, limit));
  }
}
