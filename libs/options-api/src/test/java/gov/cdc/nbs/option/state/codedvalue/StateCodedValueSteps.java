package gov.cdc.nbs.option.state.codedvalue;

import gov.cdc.nbs.testing.support.Active;
import io.cucumber.java.en.When;
import org.springframework.test.web.servlet.ResultActions;

public class StateCodedValueSteps {

  private final StateCodedValueRequester request;
  private final Active<ResultActions> response;

  StateCodedValueSteps(
      final StateCodedValueRequester request,
      final Active<ResultActions> response) {
    this.request = request;
    this.response = response;
  }

  @When("I am retrieving all the states")
  public void i_am_retrieving_all_the_states()
      throws Exception {
    response.active(request.complete());
  }

}
