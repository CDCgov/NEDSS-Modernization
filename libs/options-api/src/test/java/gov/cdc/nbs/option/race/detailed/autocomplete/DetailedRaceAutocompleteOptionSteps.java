package gov.cdc.nbs.option.race.detailed.autocomplete;

import gov.cdc.nbs.testing.support.Active;
import io.cucumber.java.en.When;
import org.springframework.test.web.servlet.ResultActions;

public class DetailedRaceAutocompleteOptionSteps {

  private final DetailedRaceAutocompleteRequester requester;
  private final Active<ResultActions> response;

  DetailedRaceAutocompleteOptionSteps(
      final DetailedRaceAutocompleteRequester requester, final Active<ResultActions> response) {
    this.requester = requester;
    this.response = response;
  }

  @When("I am trying to find {raceCategory} race details that start with {string}")
  public void i_am_trying_detailed_races_that_start_with(
      final String category, final String criteria) throws Exception {
    response.active(requester.complete(category, criteria));
  }

  @When("I am trying to find at most {int} {raceCategory} race details that start with {string}")
  public void i_am_trying_detailed_races_that_start_with(
      final int limit, final String category, final String criteria) throws Exception {
    response.active(requester.complete(category, criteria, limit));
  }
}
