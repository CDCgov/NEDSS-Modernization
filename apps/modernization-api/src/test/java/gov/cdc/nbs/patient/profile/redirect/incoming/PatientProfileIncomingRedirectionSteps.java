package gov.cdc.nbs.patient.profile.redirect.incoming;

import gov.cdc.nbs.authentication.SessionCookie;
import gov.cdc.nbs.patient.identifier.PatientIdentifier;
import gov.cdc.nbs.testing.support.Active;
import gov.cdc.nbs.testing.support.Available;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.hamcrest.CoreMatchers.startsWith;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class PatientProfileIncomingRedirectionSteps {

  @Autowired
  Available<PatientIdentifier> patients;

  @Autowired
  MockMvc mvc;

  @Autowired
  Active<SessionCookie> activeSession;

  @Autowired
  Active<ResultActions> response;

  @When("Redirecting a Classic Master Patient Record Profile")
  public void redirecting_a_classic_master_patient_record_profile() throws Exception {

    PatientIdentifier patient = patients.one();

    SessionCookie session = activeSession.maybeActive().orElse(new SessionCookie(null));

    response.active(
        mvc
            .perform(
                MockMvcRequestBuilders.post("/nbs/redirect/patientProfile")
                    .param("MPRUid", String.valueOf(patient.id()))
                    .cookie(session.asCookie())
            )
    );
  }

  @When("Redirecting a Classic Revision Patient Profile")
  public void redirecting_a_classic_revision_patient_profile() throws Exception {
    PatientIdentifier patient = patients.one();

    SessionCookie session = activeSession.maybeActive().orElse(new SessionCookie(null));

    response.active(
        mvc
            .perform(
                MockMvcRequestBuilders.post("/nbs/redirect/patientProfile")
                    .param("uid", String.valueOf(patient.id()))
                    .cookie(session.asCookie())
            )
    );
  }

  @Then("I am redirected to the Modernized Patient Profile")
  public void i_am_redirected_to_the_modernized_patient_profile() throws Exception {
    PatientIdentifier patient = patients.one();

    this.response.active()
        .andExpect(status().isSeeOther())
        .andExpect(header().string("Location", startsWith("/patient-profile/" + patient.shortId())))
        .andExpect(cookie().value("Return-Patient", ""))
        .andExpect(cookie().value("Patient-Action", ""))
    ;

  }
}
