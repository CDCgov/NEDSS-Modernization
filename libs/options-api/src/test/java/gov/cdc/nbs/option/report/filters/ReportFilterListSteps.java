package gov.cdc.nbs.option.report.filters;

import gov.cdc.nbs.testing.support.Active;
import io.cucumber.java.en.When;
import org.springframework.test.web.servlet.ResultActions;

public class ReportFilterListSteps {

  private final ReportFilterListRequester request;
  private final Active<ResultActions> response;

  ReportFilterListSteps(
      final ReportFilterListRequester request, final Active<ResultActions> response) {
    this.request = request;
    this.response = response;
  }

  @When("I am retrieving all the report filters")
  public void i_am_retrieving_all_the_report_filters() throws Exception {
    response.active(request.complete());
  }
}
