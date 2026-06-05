package gov.cdc.nbs.option.report.datasources;

import gov.cdc.nbs.testing.support.Active;
import io.cucumber.java.en.When;
import org.springframework.test.web.servlet.ResultActions;

public class ReportDataSourceListSteps {

  private final ReportDataSourceListRequester request;
  private final Active<ResultActions> response;

  ReportDataSourceListSteps(
      final ReportDataSourceListRequester request, final Active<ResultActions> response) {
    this.request = request;
    this.response = response;
  }

  @When("I am retrieving all the report data sources")
  public void i_am_retrieving_all_the_report_datasources() throws Exception {
    response.active(request.complete());
  }
}
