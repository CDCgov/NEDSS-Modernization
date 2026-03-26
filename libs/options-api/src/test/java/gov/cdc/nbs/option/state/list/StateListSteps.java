package gov.cdc.nbs.option.state.list;

import gov.cdc.nbs.testing.support.Active;
import io.cucumber.java.en.When;
import org.springframework.test.web.servlet.ResultActions;

public class StateListSteps {

  private final StateListRequester request;
  private final Active<ResultActions> response;

  StateListSteps(final StateListRequester request, final Active<ResultActions> response) {
    this.request = request;
    this.response = response;
  }

  @When("I am retrieving all the states")
  public void i_am_retrieving_all_the_states() throws Exception {
    response.active(request.complete());
  }
}
