package gov.cdc.nbs.patient.file.events.contact;

import gov.cdc.nbs.event.investigation.InvestigationIdentifier;
import gov.cdc.nbs.testing.event.contact.ContactRecordIdentifier;
import gov.cdc.nbs.testing.support.Active;
import io.cucumber.java.en.Then;
import org.springframework.test.web.servlet.ResultActions;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

public class PatientFileContactRecordVerificationSteps {

  private final Active<ContactRecordIdentifier> activeContact;
  private final Active<InvestigationIdentifier> activeInvestigation;
  private final Active<ResultActions> response;

  PatientFileContactRecordVerificationSteps(
      final Active<ContactRecordIdentifier> activeContact,
      final Active<InvestigationIdentifier> activeInvestigation,
      final Active<ResultActions> response
  ) {
    this.activeContact = activeContact;
    this.activeInvestigation = activeInvestigation;
    this.response = response;
  }

  private String local() {
    return this.activeContact.maybeActive().map(ContactRecordIdentifier::local).orElse(null);
  }

  @Then("the patient file has the patient named by the contact")
  @Then("the patient file has the contact named by the patient")
  public void found() throws Exception {
    this.response.active().andExpect(jsonPath("$.[*].contacts[?(@.local=='%s')]", local()).exists());
  }

  @Then("the patient file has the patient named by the contact {string} {string}")
  @Then("the patient file has the contact {string} {string} named by the patient")
  public void named(final String first, final String last) throws Exception {
    this.response.active()
        .andExpect(jsonPath("$.[*].contacts[?(@.local=='%s' && @.named.first=='%s' && @.named.last=='%s')]",
                local(), first, last
            ).exists()
        );
  }

  @Then("the patient file has the contact record created on {localDate} at {time}")
  public void createdOn(final LocalDate on, final LocalTime at) throws Exception {
    this.response.active()
        .andExpect(jsonPath("$.[*].contacts[?(@.local=='%s' && @.createdOn=='%s')]",
                local(), LocalDateTime.of(on, at), at
            ).exists()
        );
  }

  @Then("the patient file has the contact record named on {localDate}")
  public void namedOn(final LocalDate on) throws Exception {
    this.response.active()
        .andExpect(jsonPath("$.[*].contacts[?(@.local=='%s' && @.namedOn=='%s')]",
                local(), on
            ).exists()
        );
  }

  @Then("the patient file has the contact record with a {string} priority")
  public void priority(final String value) throws Exception {
    this.response.active()
        .andExpect(jsonPath("$.[*].contacts[?(@.local=='%s' && @.priority=='%s')]",
                local(), value
            ).exists()
        );
  }

  @Then("the patient file has the contact record with a {string} disposition")
  public void disposition(final String value) throws Exception {
    this.response.active()
        .andExpect(jsonPath("$.[*].contacts[?(@.local=='%s' && @.disposition=='%s')]",
                local(), value
            ).exists()
        );
  }

  @Then("the patient file has the contact record with a {referralBasis} referral basis")
  public void referralBasis(final String value) throws Exception {
    this.response.active()
        .andExpect(jsonPath("$.[*].contacts[?(@.local=='%s' && @.referralBasis=='%s')]",
                local(), value
            ).exists()
        );
  }

  @Then("the patient file has the contact record with a {string} processing decision")
  public void processingDecision(final String value) throws Exception {
    this.response.active()
        .andExpect(jsonPath("$.[*].contacts[?(@.local=='%s' && @.processingDecision=='%s')]",
                local(), value
            ).exists()
        );
  }

  @Then("the patient file has the contact record associated with the investigation")
  public void associated() throws Exception {
    this.response.active()
        .andExpect(jsonPath("$.[*].contacts[?(@.local=='%s' && @.associated.local=='%s')]",
                local(), this.activeInvestigation.active().local()
            ).exists()
        );
  }

  @Then("the patient file has the contact record not associated with the investigation")
  public void notAssociated() throws Exception {
    this.response.active()
        .andExpect(jsonPath("$.[*].contacts[?(@.local=='%s' && @.associated.local=='%s')]",
                local(), this.activeInvestigation.active().local()
            ).doesNotExist()
        );
  }
}
