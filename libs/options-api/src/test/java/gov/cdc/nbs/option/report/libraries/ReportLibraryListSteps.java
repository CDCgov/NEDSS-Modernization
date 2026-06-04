package gov.cdc.nbs.option.report.libraries;

import gov.cdc.nbs.testing.support.Active;
import io.cucumber.java.en.When;
import org.springframework.test.web.servlet.ResultActions;

public class ReportLibraryListSteps {

  private final ReportLibraryListRequester request;
  private final Active<ResultActions> response;

  ReportLibraryListSteps(
      final ReportLibraryListRequester request, final Active<ResultActions> response) {
    this.request = request;
    this.response = response;
  }

  @When("I am retrieving all the report libraries")
  public void i_am_retrieving_all_the_report_libraries() throws Exception {
    response.active(request.complete());
  }
}
