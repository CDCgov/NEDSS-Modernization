package gov.cdc.nbs.option.county.list;

import gov.cdc.nbs.testing.support.Active;
import io.cucumber.java.en.When;
import org.springframework.test.web.servlet.ResultActions;

public class CountyListSteps {

  private final CountyListRequester requester;
  private final Active<ResultActions> response;

  CountyListSteps(CountyListRequester requester, Active<ResultActions> response) {
    this.requester = requester;
    this.response = response;
  }

  @When("I am trying to find counties for {state} state")
  public void counties(final String state) throws Exception {
    response.active(this.requester.request(state));
  }

}
