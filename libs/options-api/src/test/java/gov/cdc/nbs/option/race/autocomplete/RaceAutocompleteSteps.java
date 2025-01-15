package gov.cdc.nbs.option.race.autocomplete;

import gov.cdc.nbs.testing.support.Active;
import io.cucumber.java.en.When;
import org.springframework.test.web.servlet.ResultActions;

public class RaceAutocompleteSteps {

  private final RaceAutocompleteRequester request;
  private final Active<ResultActions> response;

  RaceAutocompleteSteps(
      final RaceAutocompleteRequester request,
      final Active<ResultActions> response
  ) {
    this.request = request;
    this.response = response;
  }

  @When("I am trying to find races that start with {string}")
  public void i_am_trying_to_find_races_that_start_with(final String criteria) throws Exception {
    response.active(request.complete(criteria));
  }

  @When("I am trying to find at most {int} race(s) that start with {string}")
  public void i_am_trying_to_find_n_races_that_start_with(
      final int limit,
      final String criteria
  ) throws Exception {
    response.active(request.complete(criteria, limit));
  }

}
