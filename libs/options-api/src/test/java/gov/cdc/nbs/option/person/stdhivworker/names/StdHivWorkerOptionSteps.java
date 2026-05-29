package gov.cdc.nbs.option.person.stdhivworker.names;

import gov.cdc.nbs.testing.support.Active;
import io.cucumber.java.en.When;
import org.springframework.test.web.servlet.ResultActions;

public class StdHivWorkerOptionSteps {

  private final StdHivWorkerOptionRequester requester;
  private final Active<ResultActions> response;

  StdHivWorkerOptionSteps(StdHivWorkerOptionRequester requester, Active<ResultActions> response) {
    this.requester = requester;
    this.response = response;
  }

  @When("I am retrieving all the std hiv worker names")
  public void i_am_retrieving_all_the_std_hiv_worker_names() throws Exception {
    response.active(this.requester.request());
  }
}
