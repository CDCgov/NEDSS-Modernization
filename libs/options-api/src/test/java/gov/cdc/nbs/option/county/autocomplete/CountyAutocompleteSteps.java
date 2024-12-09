package gov.cdc.nbs.option.county.autocomplete;

import gov.cdc.nbs.option.AutocompleteRequester;
import gov.cdc.nbs.testing.support.Active;
import io.cucumber.java.en.When;
import org.springframework.test.web.servlet.ResultActions;

public class CountyAutocompleteSteps {

  private static final String NAME = "counties";
  private final AutocompleteRequester request;
  private final Active<ResultActions> response;

  CountyAutocompleteSteps(
      final AutocompleteRequester request,
      final Active<ResultActions> response) {
    this.request = request;
    this.response = response;
  }

  @When("I am trying to find counties that start with {string} for {string} state")
  public void i_am_trying_to_find_counties_that_start_with_for_state(final String criteria, final String state)
      throws Exception {
    response.active(request.complete(NAME, criteria, "state", state));
  }

  @When("I am trying to find at most {int} counties that start with {string} for {string} state")
  public void i_am_trying_to_find_n_counties_that_start_with(
      final int limit,
      final String criteria,
      final String state) throws Exception {
    response.active(request.complete(NAME, criteria, "state", state, limit));
  }

}
