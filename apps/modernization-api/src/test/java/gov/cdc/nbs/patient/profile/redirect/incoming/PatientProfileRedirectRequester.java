package gov.cdc.nbs.patient.profile.redirect.incoming;

import gov.cdc.nbs.patient.identifier.PatientIdentifier;
import gov.cdc.nbs.testing.interaction.http.Authenticated;
import jakarta.servlet.http.Cookie;
import org.springframework.stereotype.Component;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@Component
class PatientProfileRedirectRequester {

  private final MockMvc mvc;

  private final Authenticated authenticated;

  PatientProfileRedirectRequester(
      final MockMvc mvc,
      final Authenticated authenticated
  ) {
    this.mvc = mvc;
    this.authenticated = authenticated;
  }

  ResultActions profile(final PatientIdentifier patient) {
    try {
      return mvc.perform(
          authenticated.withSession(
              get("/nbs/redirect/patient/profile").queryParam("uid", String.valueOf(patient.id())))

      );
    } catch (Exception e) {
      throw new IllegalStateException("Unexpected error when redirecting to a modernized patient profile", e);
    }
  }

  ResultActions returningTo(final PatientIdentifier patient, final String tab) {
    try {
      return mvc.perform(authenticated.withSession(get("/nbs/redirect/patientProfile/{tab}/return", tab))
          .cookie(new Cookie("Return-Patient", String.valueOf(patient.id()))));
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }
}
