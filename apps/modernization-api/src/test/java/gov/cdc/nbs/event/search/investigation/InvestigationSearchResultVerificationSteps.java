package gov.cdc.nbs.event.search.investigation;

import gov.cdc.nbs.event.investigation.InvestigationIdentifier;
import gov.cdc.nbs.patient.identifier.PatientIdentifier;
import gov.cdc.nbs.testing.support.Active;
import io.cucumber.java.en.Then;
import org.springframework.test.web.servlet.ResultActions;

import static gov.cdc.nbs.graphql.GraphQLErrorMatchers.accessDenied;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

public class InvestigationSearchResultVerificationSteps {

  private final Active<InvestigationIdentifier> investigation;
  private final Active<PatientIdentifier> patient;
  private final Active<ResultActions> response;

  InvestigationSearchResultVerificationSteps(
      final Active<InvestigationIdentifier> investigation,
      final Active<PatientIdentifier> patient,
      final Active<ResultActions> response
  ) {
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
                String.valueOf(this.patient.active().shortId())
            )
                .exists()
        );
  }

  @Then("the Investigation search results contain the Investigation")
  public void the_investigation_search_results_contain_the_investigation() throws Exception {
    this.response.active()
        .andExpect(
            jsonPath(
                "$.data.findInvestigationsByFilter.content[?(@.id=='%s')]",
                String.valueOf(this.investigation.active().identifier())
            )
                .exists()
        );
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
}
