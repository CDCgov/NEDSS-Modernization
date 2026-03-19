package gov.cdc.nbs.patient.file.events.vaccination;

import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import gov.cdc.nbs.event.investigation.InvestigationIdentifier;
import gov.cdc.nbs.patient.profile.vaccination.VaccinationIdentifier;
import gov.cdc.nbs.testing.support.Active;
import io.cucumber.java.en.Then;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import org.springframework.test.web.servlet.ResultActions;

public class PatientFileVaccinationVerificationSteps {

  private final Active<VaccinationIdentifier> activeReport;
  private final Active<InvestigationIdentifier> activeInvestigation;
  private final Active<ResultActions> response;

  PatientFileVaccinationVerificationSteps(
      final Active<VaccinationIdentifier> activeReport,
      final Active<InvestigationIdentifier> activeInvestigation,
      final Active<ResultActions> response) {
    this.activeReport = activeReport;
    this.activeInvestigation = activeInvestigation;
    this.response = response;
  }

  @Then("the patient file has the vaccination")
  public void found() throws Exception {
    String local = local();
    this.response.active().andExpect(jsonPath("$.[?(@.local=='%s')]", local).exists());
  }

  @Then("the patient file has the vaccine created on {localDate} at {time}")
  public void createdOn(final LocalDate on, final LocalTime at) throws Exception {
    String local = local();
    this.response
        .active()
        .andExpect(
            jsonPath("$.[?(@.local=='%s' && @.createdOn=='%s')]", local, LocalDateTime.of(on, at))
                .exists());
  }

  @Then("the patient file has the vaccination performed at {string}")
  public void reportedAt(final String value) throws Exception {
    String local = local();
    this.response
        .active()
        .andExpect(jsonPath("$.[?(@.local=='%s' && @.organization=='%s')]", local, value).exists());
  }

  @Then("the patient file has the vaccination performed by {string} {string}")
  public void orderedBy(final String first, final String last) throws Exception {
    String local = local();
    this.response
        .active()
        .andExpect(
            jsonPath(
                    "$.[?(@.local=='%s' && @.provider.first=='%s' && @.provider.last=='%s')]",
                    local, first, last)
                .exists());
  }

  @Then("the patient file has the vaccination administered on {localDate}")
  public void administeredOn(final LocalDate on) throws Exception {
    String local = local();
    this.response
        .active()
        .andExpect(jsonPath("$.[?(@.local=='%s' && @.administeredOn=='%s')]", local, on).exists());
  }

  @Then("the patient file has the {string} vaccination administered")
  public void administered(final String administered) throws Exception {
    String local = local();
    this.response
        .active()
        .andExpect(
            jsonPath("$.[?(@.local=='%s' && @.administered=='%s')]", local, administered).exists());
  }

  @Then("the patient file has the vaccination associated with the investigation")
  public void associated() throws Exception {
    String local = local();

    InvestigationIdentifier investigation = this.activeInvestigation.active();

    this.response
        .active()
        .andExpect(
            jsonPath(
                    "$.[?(@.local=='%s')].associations[?(@.id==%d && @.local=='%s')]",
                    local, investigation.identifier(), investigation.local())
                .exists());
  }

  @Then("the patient file has the vaccination is not associated with any investigations")
  public void noAssociations() throws Exception {
    String local = local();

    this.response
        .active()
        .andExpect(jsonPath("$.[?(@.local=='%s')].associations", local).value(hasItem(empty())));
  }

  private String local() {
    return this.activeReport.maybeActive().map(VaccinationIdentifier::local).orElse(null);
  }
}
