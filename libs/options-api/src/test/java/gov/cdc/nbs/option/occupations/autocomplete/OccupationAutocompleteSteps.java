package gov.cdc.nbs.option.occupations.autocomplete;

import gov.cdc.nbs.testing.support.Active;
import io.cucumber.java.en.When;
import org.springframework.test.web.servlet.ResultActions;

public class OccupationAutocompleteSteps {

  private final OccupationOptionAutocompleteRequester request;
  private final Active<ResultActions> response;

  OccupationAutocompleteSteps(
      final OccupationOptionAutocompleteRequester request, final Active<ResultActions> response) {
    this.request = request;
    this.response = response;
  }

  @When("I am trying to find occupations that start with {string}")
  public void i_am_trying_to_find_options_that_start_with(final String criteria) throws Exception {
    response.active(request.complete(criteria));
  }

  @When("I am trying to find at most {int} occupation(s) that start with {string}")
  public void i_am_trying_to_find_n_options_that_start_with(final int limit, final String criteria)
      throws Exception {
    response.active(request.complete(criteria, limit));
  }
}
