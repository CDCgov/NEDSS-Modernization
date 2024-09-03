package gov.cdc.nbs.event.search.investigation;

import gov.cdc.nbs.event.investigation.InvestigationIdentifier;
import gov.cdc.nbs.patient.identifier.PatientIdentifier;
import gov.cdc.nbs.testing.support.Active;
import io.cucumber.java.en.Then;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.JsonPathResultMatchers;
import static gov.cdc.nbs.graphql.GraphQLErrorMatchers.accessDenied;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import org.hamcrest.Matcher;
import static org.hamcrest.Matchers.*;

public class InvestigationSearchResultVerificationSteps {

  private final Active<InvestigationIdentifier> investigation;
  private final Active<PatientIdentifier> patient;
  private final Active<ResultActions> response;

  InvestigationSearchResultVerificationSteps(
      final Active<InvestigationIdentifier> investigation,
      final Active<PatientIdentifier> patient,
      final Active<ResultActions> response) {
    this.investigation = investigation;
    this.patient = patient;
    this.response = response;
  }

  @Then("the Investigation search results contain the patient short id")
  public void the_investigation_search_results_contain_the_patient_short_id() throws Exception {
    this.response.active()
        .andExpect(
            jsonPath(
                "$.data.findInvestigationsByFilter.content[*].personParticipations[?(@.shortId=='%s')]",
                String.valueOf(this.patient.active().shortId()))
                    .exists());
  }

  @Then("the Investigation search results contain the Investigation")
  public void the_investigation_search_results_contain_the_investigation() throws Exception {
    this.response.active()
        .andExpect(
            jsonPath(
                "$.data.findInvestigationsByFilter.content[?(@.id=='%s')]",
                String.valueOf(this.investigation.active().identifier()))
                    .exists());
  }

  @Then("there is only one investigation search result")
  public void there_is_only_one_investigation_search_result() throws Exception {
    this.response.active()
        .andExpect(jsonPath("$.data.findInvestigationsByFilter.total").value(1));
  }

  @Then("there are no investigation search results available")
  public void there_are_no_investigation_search_results_available() throws Exception {
    this.response.active()
        .andExpect(jsonPath("$.data.findInvestigationsByFilter.total").value(0));
  }

  @Then("the Investigation search results are not accessible")
  public void the_investigation_search_results_are_not_accessible() throws Exception {
    this.response.active().andExpect(accessDenied());
  }

  @Then("the {nth} investigation search result has a(n) {string} of {string}")
  public void the_nth_search_result_has_a_x_of_y(
      final int position,
      final String field,
      final String value
  ) throws Exception {
    int index = position - 1;

    JsonPathResultMatchers pathMatcher = matchingPath(field, String.valueOf(index));

    this.response.active()
        .andExpect(pathMatcher.value(matchingValue(field, value)));
  }

  private Matcher<?> matchingValue(final String field, final String value) {
    return switch (field.toLowerCase()) {
      case "patientid","shortid" -> hasItem(Integer.parseInt(value));
      case "condition", "notification", "investigator", "status", "start date", "jurisdiction", "investigation id" -> equalTo(
          value);
      default -> hasItem(value);
    };
  }

  private JsonPathResultMatchers matchingPath(final String field, final String position) {
    return switch (field.toLowerCase()) {
      case "birthday" -> jsonPath("$.data.findInvestigationsByFilter.content[%s].personParticipations[*].birthTime",
          position);
      case "last name" -> jsonPath("$.data.findInvestigationsByFilter.content[%s].personParticipations[*].lastName",
          position);
      case "first name" -> jsonPath("$.data.findInvestigationsByFilter.content[%s].personParticipations[*].firstName",
          position);
      case "sex" -> jsonPath("$.data.findInvestigationsByFilter.content[%s].personParticipations[*].currSexCd",
          position);
      case "patientid","shortid" -> jsonPath("$.data.findInvestigationsByFilter.content[%s].personParticipations[*].shortId",
          position);
      case "condition" -> jsonPath("$.data.findInvestigationsByFilter.content[%s].cdDescTxt", position);
      case "investigation id" -> jsonPath("$.data.findInvestigationsByFilter.content[%s].localId", position);
      case "investigator" -> jsonPath("$.data.findInvestigationsByFilter.content[%s].investigatorLastName", position);
      case "jurisdiction" -> jsonPath("$.data.findInvestigationsByFilter.content[%s].jurisdictionCodeDescTxt",
          position);
      case "notification" -> jsonPath("$.data.findInvestigationsByFilter.content[%s].notificationRecordStatusCd",
          position);
      case "start date" -> jsonPath("$.data.findInvestigationsByFilter.content[%s].startedOn", position);
      case "status" -> jsonPath("$.data.findInvestigationsByFilter.content[%s].investigationStatusCd",
          position);
      default -> throw new AssertionError("Unexpected Investigation Search Result property %s".formatted(field));
    };
  }
}
