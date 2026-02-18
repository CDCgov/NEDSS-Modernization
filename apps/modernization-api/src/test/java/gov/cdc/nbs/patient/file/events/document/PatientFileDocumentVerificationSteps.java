package gov.cdc.nbs.patient.file.events.document;

import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import gov.cdc.nbs.event.document.CaseReportIdentifier;
import gov.cdc.nbs.event.investigation.InvestigationIdentifier;
import gov.cdc.nbs.testing.support.Active;
import io.cucumber.java.en.Then;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import org.springframework.test.web.servlet.ResultActions;

public class PatientFileDocumentVerificationSteps {

  private final Active<CaseReportIdentifier> activeReport;
  private final Active<InvestigationIdentifier> activeInvestigation;
  private final Active<ResultActions> response;

  PatientFileDocumentVerificationSteps(
      final Active<CaseReportIdentifier> activeReport,
      final Active<InvestigationIdentifier> activeInvestigation,
      final Active<ResultActions> response) {
    this.activeReport = activeReport;
    this.activeInvestigation = activeInvestigation;
    this.response = response;
  }

  private String local() {
    return this.activeReport.maybeActive().map(CaseReportIdentifier::local).orElse(null);
  }

  @Then("the patient file has the case report")
  public void found() throws Exception {
    this.response.active().andExpect(jsonPath("$.[?(@.local=='%s')]", local()).exists());
  }

  @Then("the patient file has the case report received on {localDate} at {time}")
  public void receivedOn(final LocalDate on, final LocalTime at) throws Exception {
    this.response
        .active()
        .andExpect(
            jsonPath(
                    "$.[?(@.local=='%s' && @.receivedOn=='%s')]", local(), LocalDateTime.of(on, at))
                .exists());
  }

  @Then("the patient file has the case report reported on {localDate}")
  public void reportedOn(final LocalDate on) throws Exception {
    this.response
        .active()
        .andExpect(jsonPath("$.[?(@.local=='%s' && @.reportedOn=='%s')]", local(), on).exists());
  }

  @Then("the patient file has the case report for the condition {string}")
  public void condition(final String value) throws Exception {
    this.response
        .active()
        .andExpect(jsonPath("$.[?(@.local=='%s' && @.condition=='%s')]", local(), value).exists());
  }

  @Then("the patient file has the case report associated with the investigation")
  public void associated() throws Exception {
    InvestigationIdentifier investigation = this.activeInvestigation.active();
    this.response
        .active()
        .andExpect(
            jsonPath(
                    "$.[?(@.local=='%s')].associations[?(@.id==%d && @.local=='%s')]",
                    local(), investigation.identifier(), investigation.local())
                .exists());
  }

  @Then("the patient file has the case report not associated with any investigations")
  public void noAssociations() throws Exception {
    this.response
        .active()
        .andExpect(jsonPath("$.[?(@.local=='%s')].associations", local()).value(hasItem(empty())));
  }
}
