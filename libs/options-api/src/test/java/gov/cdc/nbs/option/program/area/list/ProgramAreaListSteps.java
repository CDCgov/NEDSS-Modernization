package gov.cdc.nbs.option.program.area.list;

import gov.cdc.nbs.testing.support.Active;
import io.cucumber.java.en.When;
import org.springframework.test.web.servlet.ResultActions;

public class ProgramAreaListSteps {

  private final ProgramAreaListRequester request;
  private final Active<ResultActions> response;

  ProgramAreaListSteps(
      final ProgramAreaListRequester request, final Active<ResultActions> response) {
    this.request = request;
    this.response = response;
  }

  @When("I am retrieving all the program areas")
  public void i_am_retrieving_all_the_program_areas() throws Exception {
    response.active(request.complete());
  }
}
