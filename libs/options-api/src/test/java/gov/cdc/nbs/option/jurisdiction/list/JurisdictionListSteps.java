package gov.cdc.nbs.option.jurisdiction.list;

import gov.cdc.nbs.testing.support.Active;
import io.cucumber.java.en.When;
import org.springframework.test.web.servlet.ResultActions;

public class JurisdictionListSteps {

  private final JurisdictionListRequester request;
  private final Active<ResultActions> response;

  JurisdictionListSteps(
      final JurisdictionListRequester request, final Active<ResultActions> response) {
    this.request = request;
    this.response = response;
  }

  @When("I am retrieving all the jurisdictions")
  public void i_am_retrieving_all_the_jurisdictions() throws Exception {
    response.active(request.complete());
  }
}
