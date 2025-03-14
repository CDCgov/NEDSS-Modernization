package gov.cdc.nbs.option.language.primary;

import gov.cdc.nbs.testing.support.Active;
import io.cucumber.java.en.When;
import org.springframework.test.web.servlet.ResultActions;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

public class PrimaryLanguageOptionsSteps {


  private final PrimaryLanguageOptionRequester requester;
  private final Active<ResultActions> response;

  PrimaryLanguageOptionsSteps(

      final PrimaryLanguageOptionRequester requester,
      final Active<ResultActions> response
  ) {

    this.requester = requester;
    this.response = response;
  }

  @When("I request all primary languages")
  public void i_request_all() throws Exception {
    response.active(requester.request().andDo(print()));
  }

}
