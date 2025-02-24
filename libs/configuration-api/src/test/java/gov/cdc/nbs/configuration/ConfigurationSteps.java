package gov.cdc.nbs.configuration;

import gov.cdc.nbs.testing.support.Active;
import io.cucumber.java.Before;
import io.cucumber.java.en.When;
import org.springframework.test.web.servlet.ResultActions;

public class ConfigurationSteps {

  private final ConfigurationRequester requester;
  private final Active<ResultActions> response;

  ConfigurationSteps(
      final ConfigurationRequester requester,
      final Active<ResultActions> response
  ) {
    this.requester = requester;
    this.response = response;
  }

//  @Before
//  public void reset() {
//    response.reset();
//  }

  @When("I request the frontend configuration")
  public void i_request_the_frontend_configuration() throws Exception {
    this.response.active(requester.request());
  }
}
