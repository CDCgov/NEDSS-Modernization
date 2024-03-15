package gov.cdc.nbs.event.search.labreport;

import gov.cdc.nbs.testing.support.Active;
import io.cucumber.java.en.Then;
import org.springframework.test.web.servlet.ResultActions;

import static gov.cdc.nbs.graphql.GraphQLErrorMatchers.accessDenied;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

public class LabReportSearchVerificationSteps {

  private final Active<SearchableLabReport> labReport;
  private final Active<ResultActions> response;

  LabReportSearchVerificationSteps(
      final Active<SearchableLabReport> labReport,
      final Active<ResultActions> response
  ) {
    this.labReport = labReport;
    this.response = response;
  }

  @Then("the Lab Report search results contain the lab report")
  public void the_lab_report_search_results_contain_lab_report() throws Exception {
    this.response.active()
        .andExpect(
            jsonPath(
                "$.data.findLabReportsByFilter.content[?(@.id=='%s')]",
                String.valueOf(this.labReport.active().identifier())
            )
                .exists()
        );
     }

  @Then("there is only one lab report search result")
  public void there_is_only_one_patient_search_result() throws Exception {
    this.response.active()
        .andExpect(jsonPath("$.data.findLabReportsByFilter.total").value(1));
  }

  @Then("there are {int} lab report search results")
  public void there_is_are_x_patient_search_result(final int total) throws Exception {
    this.response.active()
        .andExpect(jsonPath("$.data.findLabReportsByFilter.total").value(total));
  }

  @Then("the Lab Report search results are not accessible")
  public void the_lab_report_search_results_are_not_accessible() throws Exception {
    this.response.active().andExpect(accessDenied());
  }
}
