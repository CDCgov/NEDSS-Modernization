package gov.cdc.nbs.option.race;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

import gov.cdc.nbs.testing.support.Active;
import io.cucumber.java.en.When;
import org.springframework.test.web.servlet.ResultActions;

public class RaceOptionsSteps {

  private final RaceRequester requester;
  private final Active<ResultActions> response;

  RaceOptionsSteps(final RaceRequester requester, final Active<ResultActions> response) {
    this.requester = requester;
    this.response = response;
  }

  @When("I request all races")
  public void i_request_all_conditions() throws Exception {
    response.active(requester.request().andDo(print()));
  }
}
