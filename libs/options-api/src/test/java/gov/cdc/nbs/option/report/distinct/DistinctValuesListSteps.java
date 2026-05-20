package gov.cdc.nbs.option.report.distinct;

import gov.cdc.nbs.testing.support.Active;
import io.cucumber.java.en.When;
import org.springframework.test.web.servlet.ResultActions;

public class DistinctValuesListSteps {

  private final DistinctValuesListRequester requester;
  private final Active<ResultActions> response;

  DistinctValuesListSteps(DistinctValuesListRequester requester, Active<ResultActions> response) {
    this.requester = requester;
    this.response = response;
  }

  @When("I request all distinct values for the {string} column")
  public void concepts(final String id) throws Exception {
    response.active(this.requester.request(id));
  }
}
