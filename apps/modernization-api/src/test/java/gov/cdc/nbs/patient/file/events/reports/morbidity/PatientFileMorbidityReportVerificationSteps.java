package gov.cdc.nbs.patient.file.events.reports.morbidity;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import gov.cdc.nbs.event.investigation.InvestigationIdentifier;
import gov.cdc.nbs.event.report.morbidity.MorbidityReportIdentifier;
import gov.cdc.nbs.testing.support.Active;
import io.cucumber.java.en.Then;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import org.springframework.test.web.servlet.ResultActions;

public class PatientFileMorbidityReportVerificationSteps {

  private final Active<MorbidityReportIdentifier> activeReport;
  private final Active<InvestigationIdentifier> activeInvestigation;
  private final Active<ResultActions> response;

  PatientFileMorbidityReportVerificationSteps(
      final Active<MorbidityReportIdentifier> activeReport,
      final Active<InvestigationIdentifier> activeInvestigation,
      final Active<ResultActions> response) {
    this.activeReport = activeReport;
    this.activeInvestigation = activeInvestigation;
    this.response = response;
  }

  @Then("the patient file has the morbidity report")
  public void found() throws Exception {
    String local =
        this.activeReport.maybeActive().map(MorbidityReportIdentifier::local).orElse(null);
    this.response.active().andExpect(jsonPath("$.[?(@.local=='%s')]", local).exists());
  }

  @Then("the patient file has the morbidity report within {string}")
  public void within(final String jurisdiction) throws Exception {
    String local =
        this.activeReport.maybeActive().map(MorbidityReportIdentifier::local).orElse(null);
    this.response
        .active()
        .andExpect(
            jsonPath("$.[?(@.local=='%s' && @.jurisdiction=='%s')]", local, jurisdiction).exists());
  }

  @Then("the patient file has the morbidity report for the condition {string}")
  public void condition(final String condition) throws Exception {
    String local =
        this.activeReport.maybeActive().map(MorbidityReportIdentifier::local).orElse(null);
    this.response
        .active()
        .andExpect(
            jsonPath("$.[?(@.local=='%s' && @.condition=='%s')]", local, condition).exists());
  }

  @Then("the patient file has the morbidity report ordered by {string} {string}")
  public void orderedBy(final String first, final String last) throws Exception {
    String local =
        this.activeReport.maybeActive().map(MorbidityReportIdentifier::local).orElse(null);
    this.response
        .active()
        .andExpect(
            jsonPath(
                    "$.[?(@.local=='%s' && @.orderingProvider.first=='%s' && @.orderingProvider.last=='%s')]",
                    local, first, last)
                .exists());
  }

  @Then("the patient file has the morbidity report reported at {string}")
  public void reportedAt(final String value) throws Exception {
    String local =
        this.activeReport.maybeActive().map(MorbidityReportIdentifier::local).orElse(null);
    this.response
        .active()
        .andExpect(
            jsonPath("$.[?(@.local=='%s' && @.reportingFacility=='%s')]", local, value).exists());
  }

  @Then("the patient file has the morbidity report reported by {string} {string}")
  public void reportedBy(final String first, final String last) throws Exception {
    String local =
        this.activeReport.maybeActive().map(MorbidityReportIdentifier::local).orElse(null);
    this.response
        .active()
        .andExpect(
            jsonPath(
                    "$.[?(@.local=='%s' && @.reportingProvider.first=='%s' && @.reportingProvider.last=='%s')]",
                    local, first, last)
                .exists());
  }

  @Then("the patient file has the morbidity report added on {localDate} at {time}")
  public void addedOn(final LocalDate on, final LocalTime at) throws Exception {
    String local =
        this.activeReport.maybeActive().map(MorbidityReportIdentifier::local).orElse(null);
    this.response
        .active()
        .andExpect(
            jsonPath("$.[?(@.local=='%s' && @.addedOn=='%s')]", local, LocalDateTime.of(on, at))
                .exists());
  }

  @Then("the patient file has the morbidity report reported on {localDate}")
  public void reportedOn(final LocalDate on) throws Exception {
    String local =
        this.activeReport.maybeActive().map(MorbidityReportIdentifier::local).orElse(null);
    this.response
        .active()
        .andExpect(jsonPath("$.[?(@.local=='%s' && @.reportedOn=='%s')]", local, on).exists());
  }

  @Then("the patient file has the morbidity report received on {localDate} at {time}")
  public void receivedOn(final LocalDate on, final LocalTime at) throws Exception {
    String local =
        this.activeReport.maybeActive().map(MorbidityReportIdentifier::local).orElse(null);
    this.response
        .active()
        .andExpect(
            jsonPath("$.[?(@.local=='%s' && @.receivedOn=='%s')]", local, LocalDateTime.of(on, at))
                .exists());
  }

  @Then(
      "the patient file has the morbidity report containing a(n) {string} test with the result {string}")
  public void hasResultedTest(final String test, final String result) throws Exception {
    String local =
        this.activeReport.maybeActive().map(MorbidityReportIdentifier::local).orElse(null);
    this.response
        .active()
        .andExpect(
            jsonPath("$.[?(@.local=='%s')].resultedTests[?(@.name=='%s')].result", local, test)
                .value(hasItem(containsStringIgnoringCase(result))));
  }

  @Then("the patient file has the morbidity report does not contain resulted tests")
  public void noResultedTests() throws Exception {
    String local =
        this.activeReport.maybeActive().map(MorbidityReportIdentifier::local).orElse(null);
    this.response
        .active()
        .andExpect(jsonPath("$.[?(@.local=='%s')].resultedTests]", local).isEmpty());
  }

  @Then("the patient file has the morbidity report associated with the investigation")
  public void associated() throws Exception {
    String local =
        this.activeReport.maybeActive().map(MorbidityReportIdentifier::local).orElse(null);

    InvestigationIdentifier investigation = this.activeInvestigation.active();

    this.response
        .active()
        .andExpect(
            jsonPath(
                    "$.[?(@.local=='%s')].associations[?(@.id==%d && @.local=='%s')]",
                    local, investigation.identifier(), investigation.local())
                .exists());
  }

  @Then("the patient file has the morbidity report is not associated with any investigations")
  public void noAssociations() throws Exception {
    String local =
        this.activeReport.maybeActive().map(MorbidityReportIdentifier::local).orElse(null);

    this.response
        .active()
        .andExpect(jsonPath("$.[?(@.local=='%s')].associations", local).value(hasItem(empty())));
  }

  @Then("the patient file has the morbidity report contains the {string} treatment")
  public void treatment(final String treatment) throws Exception {
    String local =
        this.activeReport.maybeActive().map(MorbidityReportIdentifier::local).orElse(null);

    this.response
        .active()
        .andExpect(
            jsonPath("$.[?(@.local=='%s')].treatments[?(@=='%s')]", local, treatment).exists());
  }

  @Then("the patient file has the morbidity report does not contain treatments")
  public void noTreatments() throws Exception {
    String local =
        this.activeReport.maybeActive().map(MorbidityReportIdentifier::local).orElse(null);

    this.response.active().andExpect(jsonPath("$.[?(@.local=='%s')].treatments]", local).isEmpty());
  }
}
