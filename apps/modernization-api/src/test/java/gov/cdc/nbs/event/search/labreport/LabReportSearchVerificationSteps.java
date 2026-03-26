package gov.cdc.nbs.event.search.labreport;

import static gov.cdc.nbs.graphql.GraphQLErrorMatchers.accessDenied;
import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import gov.cdc.nbs.patient.identifier.PatientIdentifier;
import gov.cdc.nbs.testing.support.Active;
import io.cucumber.java.en.Then;
import org.hamcrest.Matcher;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.JsonPathResultMatchers;

public class LabReportSearchVerificationSteps {

  private final Active<PatientIdentifier> patient;
  private final Active<SearchableLabReport> labReport;
  private final Active<ResultActions> response;

  LabReportSearchVerificationSteps(
      final Active<PatientIdentifier> patient,
      final Active<SearchableLabReport> labReport,
      final Active<ResultActions> response) {
    this.patient = patient;
    this.labReport = labReport;
    this.response = response;
  }

  @Then("the Lab Report search results contain the lab report")
  public void the_lab_report_search_results_contain_lab_report() throws Exception {
    this.response
        .active()
        .andExpect(
            jsonPath(
                    "$.data.findLabReportsByFilter.content[?(@.id=='%s')]",
                    String.valueOf(this.labReport.active().identifier()))
                .exists());
  }

  @Then("the Lab Report search results do not contain the lab report")
  public void the_lab_report_search_results_do_not_contain_lab_report() throws Exception {
    this.response
        .active()
        .andExpect(
            jsonPath(
                    "$.data.findLabReportsByFilter.content[?(@.id=='%s')]",
                    String.valueOf(this.labReport.active().identifier()))
                .doesNotExist());
  }

  @Then("the Lab Report search results contain the patient short id")
  public void the_lab_report_search_results_contain_the_patient_short_id() throws Exception {
    this.response
        .active()
        .andExpect(
            jsonPath(
                    "$.data.findLabReportsByFilter.content[*].personParticipations[?(@.shortId=='%s')]",
                    String.valueOf(this.patient.active().shortId()))
                .exists());
  }

  @Then("there are no lab report search results available")
  public void there_are_no_lab_report_search_results_available() throws Exception {
    this.response.active().andExpect(jsonPath("$.data.findLabReportsByFilter.total").value(0));
  }

  @Then("there is only one lab report search result")
  public void there_is_only_one_lab_report_search_result() throws Exception {
    this.response.active().andExpect(jsonPath("$.data.findLabReportsByFilter.total").value(1));
  }

  @Then("there are {int} lab report search results available")
  public void there_is_are_x_lab_report_search_result(final int total) throws Exception {
    this.response.active().andExpect(jsonPath("$.data.findLabReportsByFilter.total").value(total));
  }

  @Then("the Lab Report search results are not accessible")
  public void the_lab_report_search_results_are_not_accessible() throws Exception {
    this.response.active().andExpect(accessDenied());
  }

  @Then("the {nth} lab report search result has a(n) {string} of {string}")
  public void the_nth_search_result_has_a_x_of_y(
      final int position, final String field, final String value) throws Exception {
    int index = position - 1;

    JsonPathResultMatchers pathMatcher = matchingPath(field, String.valueOf(index));

    this.response.active().andExpect(pathMatcher.value(matchingValue(field, value)));
  }

  private Matcher<?> matchingValue(final String field, final String value) {
    return switch (field.toLowerCase()) {
      case "local id" -> hasItem(Integer.parseInt(value));
      default -> hasItem(value);
    };
  }

  private JsonPathResultMatchers matchingPath(final String field, final String position) {
    return switch (field.toLowerCase()) {
      case "birthday" ->
          jsonPath(
              "$.data.findLabReportsByFilter.content[%s].personParticipations[*].birthTime",
              position);
      case "last name" ->
          jsonPath(
              "$.data.findLabReportsByFilter.content[%s].personParticipations[*].lastName",
              position);
      case "first name" ->
          jsonPath(
              "$.data.findLabReportsByFilter.content[%s].personParticipations[*].firstName",
              position);
      case "sex" ->
          jsonPath(
              "$.data.findLabReportsByFilter.content[%s].personParticipations[*].currSexCd",
              position);
      case "local id" ->
          jsonPath(
              "$.data.findLabReportsByFilter.content[%s].personParticipations[*].shortId",
              position);
      default ->
          throw new AssertionError(
              "Unexpected Lab Report Search Result property %s".formatted(field));
    };
  }
}
