package gov.cdc.nbs.option.regions.list;

import gov.cdc.nbs.testing.support.Active;
import io.cucumber.java.en.When;
import org.springframework.test.web.servlet.ResultActions;

public class RegionListSteps {

  private final RegionListRequester request;
  private final Active<ResultActions> response;

  RegionListSteps(final RegionListRequester request, final Active<ResultActions> response) {
    this.request = request;
    this.response = response;
  }

  @When("I am retrieving all the regions")
  public void i_am_retrieving_all_the_regions() throws Exception {
    response.active(request.complete());
  }
}
