package gov.cdc.nbs.option.report.datasources;

import gov.cdc.nbs.testing.support.Active;
import io.cucumber.java.en.When;
import org.springframework.test.web.servlet.ResultActions;

public class ReportDataSourceColumnsListSteps {

  private final ReportDataSourceColumnsListRequester request;
  private final Active<ResultActions> response;

  ReportDataSourceColumnsListSteps(
      final ReportDataSourceColumnsListRequester request, final Active<ResultActions> response) {
    this.request = request;
    this.response = response;
  }

  @When("I am retrieving all the report columns for data source {string}")
  public void i_am_retrieving_all_the_report_datasources(final String dataSource) throws Exception {
    response.active(request.complete(dataSource));
  }
}
