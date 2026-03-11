package gov.cdc.nbs.option.condition;

import gov.cdc.nbs.testing.support.Active;
import io.cucumber.java.Before;
import io.cucumber.java.en.When;
import org.springframework.test.web.servlet.ResultActions;

public class ConditionOptionsSteps {

  private final ConditionRequester requester;
  private final Active<ResultActions> response;

  ConditionOptionsSteps(final ConditionRequester requester, final Active<ResultActions> response) {
    this.requester = requester;
    this.response = response;
  }

  @Before("@condition")
  public void reset() {
    response.reset();
  }

  @When("I request all conditions")
  public void i_request_all_conditions() throws Exception {
    response.active(requester.request());
  }
}
