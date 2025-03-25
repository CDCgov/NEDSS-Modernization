package gov.cdc.nbs.option.codedvalues;

import gov.cdc.nbs.testing.support.Active;
import io.cucumber.java.en.When;
import org.springframework.test.web.servlet.ResultActions;

public class CodedValuesSteps {

  private final CodedValuesRequester request;
  private final Active<ResultActions> response;

  CodedValuesSteps(
      final CodedValuesRequester request,
      final Active<ResultActions> response) {
    this.request = request;
    this.response = response;
  }

  @When("I am retrieving all the {string}")
  public void i_am_retrieving_all_the_states(String path)
      throws Exception {
    response.active(request.complete(path));
  }

}
