package gov.cdc.nbs.patient.investigation;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import gov.cdc.nbs.support.provider.ProviderIdentifier;
import gov.cdc.nbs.testing.authorization.jurisdiction.JurisdictionIdentifier;
import gov.cdc.nbs.testing.support.Active;
import io.cucumber.java.en.Then;
import java.time.LocalDate;
import org.springframework.test.web.servlet.ResultActions;

public class PatientFileInvestigationsVerificationSteps {

  private final Active<ResultActions> response;
  private final Active<ProviderIdentifier> activeProvider;

  PatientFileInvestigationsVerificationSteps(
      final Active<ResultActions> response, final Active<ProviderIdentifier> activeProvider) {
    this.response = response;
    this.activeProvider = activeProvider;
  }

  @Then("the patient file has {int} investigation(s)")
  public void found(final int count) throws Exception {
    this.response.active().andExpect(jsonPath("$.[*]").value(hasSize(count)));
  }

  @Then("the {nth} investigation is Open")
  public void nthIsOpen(final int position) throws Exception {
    nthHasStatus(position, "Open");
  }

  @Then("the {nth} investigation is Closed")
  public void nthIsClosed(final int position) throws Exception {
    nthHasStatus(position, "Closed");
  }

  private void nthHasStatus(final int position, final String status) throws Exception {
    this.response.active().andExpect(jsonPath("$[%s].status", position - 1).value(status));
  }

  @Then("the {nth} investigation is within the {jurisdiction} jurisdiction")
  public void nthWithin(final int position, final JurisdictionIdentifier value) throws Exception {
    this.response
        .active()
        .andExpect(jsonPath("$[%s].jurisdiction", position - 1).value(value.name()));
  }

  @Then("the {nth} investigation is for the {string} condition")
  public void nthHasCondition(final int position, final String value) throws Exception {
    this.response.active().andExpect(jsonPath("$[%s].condition", position - 1).value(value));
  }

  @Then("the {nth} investigation was started on {localDate}")
  public void nthWasStartedOn(final int position, final LocalDate value) throws Exception {
    this.response
        .active()
        .andExpect(jsonPath("$[%s].startedOn", position - 1).value(value.toString()));
  }

  @Then("the {nth} investigation was investigated by the provider")
  public void nthInvestigatedBy(final int position) throws Exception {

    ProviderIdentifier.Name provider = this.activeProvider.active().name();

    this.response
        .active()
        .andExpect(jsonPath("$[%s].investigator.first", position - 1).value(provider.first()))
        .andExpect(jsonPath("$[%s].investigator.last", position - 1).value(provider.last()));
  }
}
