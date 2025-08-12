package gov.cdc.nbs.patient.file.events.record.birth;

import gov.cdc.nbs.event.investigation.InvestigationIdentifier;
import gov.cdc.nbs.testing.event.record.birth.BirthRecordIdentifier;
import gov.cdc.nbs.testing.support.Active;
import io.cucumber.java.en.Then;
import org.springframework.test.web.servlet.ResultActions;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

public class PatientFileBirthRecordVerificationSteps {

  private final Active<BirthRecordIdentifier> activeRecord;
  private final Active<InvestigationIdentifier> activeInvestigation;
  private final Active<ResultActions> response;

  public PatientFileBirthRecordVerificationSteps(Active<BirthRecordIdentifier> activeRecord,
      Active<InvestigationIdentifier> activeInvestigation, Active<ResultActions> response) {
    this.activeRecord = activeRecord;
    this.activeInvestigation = activeInvestigation;
    this.response = response;
  }

  private String local() {
    return this.activeRecord.maybeActive().map(BirthRecordIdentifier::local).orElse(null);
  }

  @Then("the patient file has the birth record")
  public void found() throws Exception {
    this.response.active().andExpect(jsonPath("$.[?(@.local=='%s')]", local()).exists());
  }

  @Then("the patient file has the birth record received on {localDate} at {time}")
  public void receivedOn(
      final LocalDate on,
      final LocalTime at
  ) throws Exception {
    this.response.active()
        .andExpect(
            jsonPath(
                "$.[?(@.local=='%s' && @.receivedOn=='%s')]",
                local(), LocalDateTime.of(on, at)
            ).exists()
        );
  }

  @Then("the patient file has the birth record born at {string}")
  public void bornAt(final String value) throws Exception {
    this.response.active().andExpect(
        jsonPath("$.[?(@.local=='%s' && @.facility=='%s')]",
            local(), value
        ).exists()
    );
  }

  @Then("the patient file has the birth record for the {string} certificate")
  public void certificate(final String treatment) throws Exception {
    String local = local();
    this.response.active()
        .andExpect(
            jsonPath(
                "$.[?(@.local=='%s' && @.certificate=='%s')]",
                local, treatment
            ).exists()
        );
  }

  @Then("the patient file has the birth record collected on {localDate}")
  public void collectedOn(final LocalDate on) throws Exception {
    String local = local();
    this.response.active()
        .andExpect(
            jsonPath(
                "$.[?(@.local=='%s' && @.collectedOn=='%s')]",
                local, on
            ).exists()
        );
  }

  @Then("the patient file has the birth record associated with the investigation")
  public void associated() throws Exception {
    InvestigationIdentifier investigation = this.activeInvestigation.active();

    this.response.active()
        .andExpect(
            jsonPath(
                "$.[?(@.local=='%s')].associations[?(@.id==%d && @.local=='%s')]",
                local(), investigation.identifier(), investigation.local()
            ).exists()
        );
  }

  @Then("the patient file has the birth record not associated with any investigations")
  public void noAssociations() throws Exception {
    this.response.active()
        .andExpect(
            jsonPath(
                "$.[?(@.local=='%s')].associations",
                local()
            ).value(hasItem(empty()))
        );
  }
}


