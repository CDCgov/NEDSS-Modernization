package gov.cdc.nbs.patient.profile.redirect.incoming;

import gov.cdc.nbs.patient.identifier.PatientIdentifier;
import gov.cdc.nbs.testing.interaction.http.Authenticated;
import gov.cdc.nbs.testing.support.Active;
import io.cucumber.java.en.When;
import jakarta.servlet.http.Cookie;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

public class PatientProfileReturningRedirectionSteps {

  private final Authenticated authenticated;

  private final Active<PatientIdentifier> activePatient;

  private final MockMvc mvc;

  private final Active<ResultActions> activeResponse;

  PatientProfileReturningRedirectionSteps(
      final Authenticated authenticated,
      final Active<PatientIdentifier> activePatient,
      final MockMvc mvc,
      final Active<ResultActions> activeResponse
  ) {
    this.authenticated = authenticated;
    this.activePatient = activePatient;
    this.mvc = mvc;
    this.activeResponse = activeResponse;
  }

  @When("Returning to a Patient Profile")
  public void returning_to_a_patient_profile() throws Exception {

    long patient = activePatient.active().id();

    activeResponse.active(
        mvc.perform(
            this.authenticated.withSession(get("/nbs/redirect/patientProfile/return"))
                .cookie(new Cookie("Return-Patient", String.valueOf(patient))))
    );
  }

  @When("Returning to a Patient Profile {patientProfileTab} tab")
  public void returning_to_a_patient_profile_tab(final String tab) throws Exception {

    long patient = activePatient.active().id();

    activeResponse.active(
        mvc.perform(authenticated.withSession(get("/nbs/redirect/patientProfile/{tab}/return", tab))
            .cookie(new Cookie("Return-Patient", String.valueOf(patient))))
    );
  }
}
