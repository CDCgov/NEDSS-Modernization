package gov.cdc.nbs.patient.profile.redirect.incoming;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.startsWith;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import gov.cdc.nbs.patient.identifier.PatientIdentifier;
import gov.cdc.nbs.testing.interaction.http.Authenticated;
import gov.cdc.nbs.testing.support.Active;
import gov.cdc.nbs.testing.support.Available;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

public class PatientProfileIncomingRedirectionSteps {

  private final Available<PatientIdentifier> patients;
  private final Authenticated authenticated;
  private final MockMvc mvc;
  private final Active<ResultActions> response;

  PatientProfileIncomingRedirectionSteps(
      final Available<PatientIdentifier> patients,
      final Authenticated authenticated,
      final MockMvc mvc,
      final Active<ResultActions> response) {
    this.patients = patients;
    this.authenticated = authenticated;
    this.mvc = mvc;
    this.response = response;
  }

  @When("Redirecting a Classic Master Patient Record Profile")
  public void redirecting_a_classic_master_patient_record_profile() throws Exception {

    PatientIdentifier patient = patients.one();

    response.active(
        mvc.perform(
            authenticated
                .withSession(post("/nbs/redirect/patient/file"))
                .param("MPRUid", String.valueOf(patient.id()))));
  }

  @When("Redirecting a Classic Revision Patient Profile")
  public void redirecting_a_classic_revision_patient_profile() throws Exception {
    PatientIdentifier patient = patients.one();

    response.active(
        mvc.perform(
            authenticated
                .withSession(post("/nbs/redirect/patient/file"))
                .param("uid", String.valueOf(patient.id()))));
  }

  @Then("I am redirected to the Modernized Patient Profile")
  public void i_am_redirected_to_the_modernized_patient_profile() throws Exception {
    PatientIdentifier patient = patients.one();

    this.response
        .active()
        .andExpect(status().isSeeOther())
        .andExpect(header().string("Location", startsWith("/patient/" + patient.shortId())))
        .andExpect(cookie().value("Return-Patient", ""))
        .andExpect(cookie().value("Patient-Action", ""));
  }

  @Then("I am redirected to the Modernized Patient Profile {patientProfileTab} tab")
  public void i_am_redirected_to_the_modernized_patient_profile_tab(final String tab)
      throws Exception {
    PatientIdentifier patient = patients.one();

    this.response
        .active()
        .andExpect(status().isSeeOther())
        .andExpect(
            header().string("Location", equalTo("/patient/" + patient.shortId() + "/" + tab)))
        .andExpect(cookie().value("Return-Patient", ""))
        .andExpect(cookie().value("Patient-Action", ""));
  }
}
