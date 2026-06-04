package gov.cdc.nbs.option.report.sections;

import gov.cdc.nbs.testing.support.Active;
import io.cucumber.java.en.When;
import org.springframework.test.web.servlet.ResultActions;

public class ReportSectionListSteps {

  private final ReportSectionListRequester request;
  private final Active<ResultActions> response;

  ReportSectionListSteps(
      final ReportSectionListRequester request, final Active<ResultActions> response) {
    this.request = request;
    this.response = response;
  }

  @When("I am retrieving all the report sections")
  public void i_am_retrieving_all_the_report_sections() throws Exception {
    response.active(request.complete());
  }
}
