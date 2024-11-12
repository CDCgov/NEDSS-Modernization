package gov.cdc.nbs.patient.profile.redirect.incoming;

import gov.cdc.nbs.event.investigation.InvestigationIdentifier;
import gov.cdc.nbs.testing.interaction.http.Authenticated;
import jakarta.servlet.http.Cookie;
import org.springframework.stereotype.Component;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@Component
class PatientProfileFromActionRequester {

  private final MockMvc mvc;

  private final Authenticated authenticated;

  PatientProfileFromActionRequester(
      final MockMvc mvc,
      final Authenticated authenticated
  ) {
    this.mvc = mvc;
    this.authenticated = authenticated;
  }

  ResultActions returning(final InvestigationIdentifier investigation) {
    try {
      return mvc.perform(
          authenticated.withSession(get("/nbs/redirect/patientProfile/summary/return"))
              .cookie(new Cookie("Patient-Action", String.valueOf(investigation.identifier())))
      );
    } catch (Exception e) {
      throw new IllegalStateException("Unexpected error when returning to a patient profile from an Investigation", e);
    }
  }

}
