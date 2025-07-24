package gov.cdc.nbs.patient.file.events.reports.laboratory;

import gov.cdc.nbs.event.investigation.InvestigationIdentifier;
import gov.cdc.nbs.event.report.lab.LabReportIdentifier;
import gov.cdc.nbs.testing.support.Active;
import io.cucumber.java.en.Then;
import org.springframework.test.web.servlet.ResultActions;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

public class PatientFileLaboratoryReportVerificationSteps {

  private final Active<LabReportIdentifier> activeReport;
  private final Active<InvestigationIdentifier> activeInvestigation;
  private final Active<ResultActions> response;

  PatientFileLaboratoryReportVerificationSteps(
      final Active<LabReportIdentifier> activeReport,
      final Active<InvestigationIdentifier> activeInvestigation,
      final Active<ResultActions> response
  ) {
    this.activeReport = activeReport;
    this.activeInvestigation = activeInvestigation;
    this.response = response;
  }

  @Then("the patient file has the lab(oratory) report")
  public void found() throws Exception {
    String local = this.activeReport.maybeActive().map(LabReportIdentifier::local).orElse(null);
    this.response.active().andExpect(jsonPath("$.[?(@.local=='%s')]", local).exists());
  }

  @Then("the patient file has the lab(oratory) report for {string} within {string}")
  public void within(final String area, final String jurisdiction) throws Exception {
    String local = this.activeReport.maybeActive().map(LabReportIdentifier::local).orElse(null);
    this.response.active().andExpect(
        jsonPath("$.[?(@.local=='%s' && @.programArea=='%s' && @.jurisdiction=='%s')]",
            local, area, jurisdiction
        ).exists()
    );
  }

  @Then("the patient file has the lab(oratory) report reported at {string}")
  public void reportedAt(final String value) throws Exception {
    String local = this.activeReport.maybeActive().map(LabReportIdentifier::local).orElse(null);
    this.response.active().andExpect(
        jsonPath("$.[?(@.local=='%s' && @.reportingFacility=='%s')]",
            local, value
        ).exists()
    );
  }

  @Then("the patient file has the laboratory report ordered by {string} {string}")
  public void orderedBy(final String first, final String last) throws Exception {
    String local = this.activeReport.maybeActive().map(LabReportIdentifier::local).orElse(null);
    this.response.active().andExpect(
        jsonPath("$.[?(@.local=='%s' && @.orderingProvider.first=='%s' && @.orderingProvider.last=='%s')]",
            local, first, last
        ).exists()
    );
  }

  @Then("the patient file has the lab(oratory) report ordered at {string}")
  public void orderedAt(final String value) throws Exception {
    String local = this.activeReport.maybeActive().map(LabReportIdentifier::local).orElse(null);
    this.response.active().andExpect(
        jsonPath("$.[?(@.local=='%s' && @.orderingFacility=='%s')]",
            local, value
        ).exists()
    );
  }

  @Then("the patient file has the lab(oratory) report received on {localDate} at {time}")
  public void receivedOn(
      final LocalDate on,
      final LocalTime at
  ) throws Exception {
    String local = this.activeReport.maybeActive().map(LabReportIdentifier::local).orElse(null);
    this.response.active()
        .andExpect(
            jsonPath(
                "$.[?(@.local=='%s' && @.receivedDate=='%s')]",
                local, LocalDateTime.of(on, at)
            ).exists()
        );
  }

  @Then("the patient file has the lab(oratory) report collected on {localDate}")
  public void collectedOn(
      final LocalDate on
  ) throws Exception {
    String local = this.activeReport.maybeActive().map(LabReportIdentifier::local).orElse(null);
    this.response.active()
        .andExpect(
            jsonPath(
                "$.[?(@.local=='%s' && @.collectedDate=='%s')]",
                local, on
            ).exists()
        );
  }

  @Then("the patient file has the laboratory report as electronic")
  public void electronic() throws Exception {
    electronic(true);
  }

  @Then("the patient file has the laboratory report as not electronic")
  public void notElectronic() throws Exception {
    electronic(false);
  }

  private void electronic(final boolean value) throws Exception {
    String local = this.activeReport.maybeActive().map(LabReportIdentifier::local).orElse(null);
    this.response.active()
        .andExpect(
            jsonPath(
                "$.[?(@.local=='%s' && @.electronic==%s)]",
                local, value
            ).exists()
        );
  }

  @Then("the patient file has the laboratory report containing a(n) {string} test with the result {string}")
  public void hasResultedTest(final String test, final String result) throws Exception {
    String local = this.activeReport.maybeActive().map(LabReportIdentifier::local).orElse(null);
    this.response.active()
        .andExpect(
            jsonPath("$.[?(@.local=='%s')].resultedTests[?(@.name=='%s')].result", local, test)
                .value(hasItem(containsStringIgnoringCase(result)))
        );
  }

  @Then("the patient file has the laboratory report does not contain resulted tests")
  public void noResultedTests() throws Exception {
    String local = this.activeReport.maybeActive().map(LabReportIdentifier::local).orElse(null);
    this.response.active()
        .andExpect(
            jsonPath("$.[?(@.local=='%s')].resultedTests]", local).isEmpty()
        );
  }

  @Then("the patient file has the laboratory report associated with the investigation")
  public void associated() throws Exception {
    String local = this.activeReport.maybeActive().map(LabReportIdentifier::local).orElse(null);

    InvestigationIdentifier investigation = this.activeInvestigation.active();

    this.response.active()
        .andExpect(
            jsonPath(
                "$.[?(@.local=='%s')].associations[?(@.id==%d && @.local=='%s')]",
                local, investigation.identifier(), investigation.local()
            ).exists()
        );
  }

  @Then("the patient file has the laboratory report is not associated with any investigations")
  public void noAssociations() throws Exception {
    String local = this.activeReport.maybeActive().map(LabReportIdentifier::local).orElse(null);

    this.response.active()
        .andExpect(
            jsonPath(
                "$.[?(@.local=='%s')].associations",
                local
            ).value(hasItem(empty()))
        );
  }

  @Then("the patient file has the laboratory report containing an ordered test with a specimen from the {string}")
  public void specimenSite(final String value) throws Exception {
    String local = this.activeReport.maybeActive().map(LabReportIdentifier::local).orElse(null);
    this.response.active()
        .andExpect(
            jsonPath(
                "$.[?(@.local=='%s' && @.specimen.site=='%s')]",
                local, value
            ).exists()
        );
  }

  @Then(
      "the patient file has the laboratory report containing an ordered test with a {string} specimen from the {string}")
  public void specimenSource(final String source, final String site) throws Exception {
    String local = this.activeReport.maybeActive().map(LabReportIdentifier::local).orElse(null);
    this.response.active()
        .andExpect(
            jsonPath(
                "$.[?(@.local=='%s' && @.specimen.source=='%s' && @.specimen.site=='%s')]",
                local, source, site
            ).exists()
        );
  }
}
