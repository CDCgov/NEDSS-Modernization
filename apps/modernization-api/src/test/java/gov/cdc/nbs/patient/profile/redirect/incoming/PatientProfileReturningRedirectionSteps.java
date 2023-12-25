package gov.cdc.nbs.patient.profile.redirect.incoming;

import gov.cdc.nbs.authentication.SessionCookie;
import gov.cdc.nbs.patient.identifier.PatientIdentifier;
import gov.cdc.nbs.testing.interaction.http.Authenticated;
import gov.cdc.nbs.testing.support.Active;
import io.cucumber.java.en.When;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import javax.servlet.http.Cookie;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

public class PatientProfileReturningRedirectionSteps {

  @Autowired
  Authenticated authenticated;

  @Autowired
  Active<PatientIdentifier> activePatient;

  @Autowired
  MockMvc mvc;

  @Autowired
  Active<SessionCookie> activeSession;

  @Autowired
  Active<ResultActions> activeResponse;

  @When("Returning to a Patient Profile")
  public void returning_to_a_patient_profile() throws Exception {

    long patient = activePatient.active().id();

    activeResponse.active(
        mvc.perform(
            this.authenticated.withSession(get("/nbs/redirect/patientProfile/return"))
                .cookie(new Cookie("Return-Patient", String.valueOf(patient))))
    );
  }

  @When("Returning to a Patient Profile {string} tab")
  public void returning_to_a_patient_profile_tab(final String tab) throws Exception {

    long patient = activePatient.active().id();

    activeResponse.active(
        mvc.perform(authenticated.withSession(get("/nbs/redirect/patientProfile/{tab}/return", tab))
            .cookie(new Cookie("Return-Patient", String.valueOf(patient))))
    );
  }
}
