package gov.cdc.nbs.event.search.labreport;

import gov.cdc.nbs.testing.support.Active;
import io.cucumber.java.en.Then;
import org.springframework.test.web.servlet.ResultActions;

import static gov.cdc.nbs.graphql.GraphQLErrorMatchers.accessDenied;
import static org.hamcrest.Matchers.greaterThan;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

public class LabReportSearchVerificationSteps {

  private final Active<ResultActions> response;

  LabReportSearchVerificationSteps(final Active<ResultActions> response) {
    this.response = response;
  }

  @Then("I find the lab report")
  public void i_find_the_lab_report() throws Exception {
    this.response.active()
        .andExpect(jsonPath("$.data.findLabReportsByFilter.total").value(greaterThan(0)));

  }


  @Then("the Lab Report search results are not accessible")
  public void the_lab_report_search_results_are_not_accessible() throws Exception {
    this.response.active().andExpect(accessDenied());
  }
}
