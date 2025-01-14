package gov.cdc.nbs.option.race.detailed;

import gov.cdc.nbs.testing.support.Active;
import io.cucumber.java.en.When;
import org.springframework.test.web.servlet.ResultActions;

public class DetailedRaceOptionSteps {

  private final DetailedRaceRequester requester;
  private final Active<ResultActions> response;

  DetailedRaceOptionSteps(
      final DetailedRaceRequester requester,
      final Active<ResultActions> response
  ) {
    this.requester = requester;
    this.response = response;
  }

  @When("I am trying to find {raceCategory} race details")
  public void i_am_trying_detailed_races(final String category) throws Exception {
    response.active(requester.request(category));
  }

}
