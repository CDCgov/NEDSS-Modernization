package gov.cdc.nbs.patient.file.events.treatment;

import gov.cdc.nbs.event.investigation.InvestigationIdentifier;
import gov.cdc.nbs.testing.event.treatment.TreatmentIdentifier;
import gov.cdc.nbs.testing.support.Active;
import io.cucumber.java.en.Then;
import org.springframework.test.web.servlet.ResultActions;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

public class PatientFileTreatmentVerificationSteps {

  private final Active<TreatmentIdentifier> activeReport;
  private final Active<InvestigationIdentifier> activeInvestigation;
  private final Active<ResultActions> response;

  PatientFileTreatmentVerificationSteps(
      final Active<TreatmentIdentifier> activeReport,
      final Active<InvestigationIdentifier> activeInvestigation,
      final Active<ResultActions> response
  ) {
    this.activeReport = activeReport;
    this.activeInvestigation = activeInvestigation;
    this.response = response;
  }

  @Then("the patient file has the treatment")
  public void found() throws Exception {
    String local = local();
    this.response.active().andExpect(jsonPath("$.[?(@.local=='%s')]", local).exists());
  }

  @Then("the patient file has the treatment created on {localDate} at {time}")
  public void createdOn(
      final LocalDate on,
      final LocalTime at
  ) throws Exception {
    String local = local();
    this.response.active()
        .andExpect(
            jsonPath(
                "$.[?(@.local=='%s' && @.createdOn=='%s')]",
                local, LocalDateTime.of(on, at)
            ).exists()
        );
  }

  @Then("the patient file has the treatment reported at {string}")
  public void reportedAt(final String value) throws Exception {
    String local = local();
    this.response.active().andExpect(
        jsonPath("$.[?(@.local=='%s' && @.organization=='%s')]",
            local, value
        ).exists()
    );
  }

  @Then("the patient file has the treatment provided by {string} {string}")
  public void orderedBy(final String first, final String last) throws Exception {
    String local = local();
    this.response.active().andExpect(
        jsonPath("$.[?(@.local=='%s' && @.provider.first=='%s' && @.provider.last=='%s')]",
            local, first, last
        ).exists()
    );
  }

  @Then("the patient file has the treatment treated on {localDate}")
  public void treatedOn(final LocalDate on) throws Exception {
    String local = local();
    this.response.active()
        .andExpect(
            jsonPath(
                "$.[?(@.local=='%s' && @.treatedOn=='%s')]",
                local, on
            ).exists()
        );
  }

  @Then("the patient file has the treatment as {string}")
  public void treatment(final String treatment) throws Exception {
    String local = local();
    this.response.active()
        .andExpect(
            jsonPath(
                "$.[?(@.local=='%s' && @.description=='%s')]",
                local, treatment
            ).exists()
        );
  }


  @Then("the patient file has the treatment associated with the investigation")
  public void associated() throws Exception {
    String local = local();

    InvestigationIdentifier investigation = this.activeInvestigation.active();

    this.response.active()
        .andExpect(
            jsonPath(
                "$.[?(@.local=='%s')].associations[?(@.id==%d && @.local=='%s')]",
                local, investigation.identifier(), investigation.local()
            ).exists()
        );
  }

  @Then("the patient file has the treatment is not associated with any investigations")
  public void noAssociations() throws Exception {
    String local = local();

    this.response.active()
        .andExpect(
            jsonPath(
                "$.[?(@.local=='%s')].associations",
                local
            ).value(hasItem(empty()))
        );
  }

  private String local() {
    return this.activeReport.maybeActive().map(TreatmentIdentifier::local).orElse(null);
  }
}
