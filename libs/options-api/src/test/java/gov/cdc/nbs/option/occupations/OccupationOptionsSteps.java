package gov.cdc.nbs.option.occupations;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

import gov.cdc.nbs.testing.support.Active;
import io.cucumber.java.en.When;
import org.springframework.test.web.servlet.ResultActions;

public class OccupationOptionsSteps {

  private final OccupationOptionRequester requester;
  private final Active<ResultActions> response;

  OccupationOptionsSteps(
      final OccupationOptionRequester requester, final Active<ResultActions> response) {

    this.requester = requester;
    this.response = response;
  }

  @When("I request all occupations")
  public void i_request_all() throws Exception {
    response.active(requester.request().andDo(print()));
  }
}
