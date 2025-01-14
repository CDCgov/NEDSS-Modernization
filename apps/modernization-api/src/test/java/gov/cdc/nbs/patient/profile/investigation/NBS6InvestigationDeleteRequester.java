package gov.cdc.nbs.patient.profile.investigation;

import gov.cdc.nbs.patient.identifier.PatientIdentifier;
import gov.cdc.nbs.testing.interaction.http.Authenticated;
import jakarta.servlet.http.Cookie;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@Component
class NBS6InvestigationDeleteRequester {


  private final MockMvc mvc;

  private final Authenticated authenticated;

  NBS6InvestigationDeleteRequester(
      final MockMvc mvc,
      final Authenticated authenticated
  ) {
    this.mvc = mvc;
    this.authenticated = authenticated;
  }

  public ResultActions delete(final PatientIdentifier patient, NBS6InvestigationRequest context) {

    try {
      return mvc.perform(
          authenticated.withSession(post("/nbs/redirect/patient/investigation/delete"))
              .param("ContextAction", context.contextAction())
              .param("method", "deleteSubmit")
              .contentType(MediaType.APPLICATION_FORM_URLENCODED)
              .cookie(new Cookie("Return-Patient", String.valueOf(patient.id()))));
    } catch (Exception exception) {
      throw new IllegalStateException("Unable to request deletion of an investigation", exception);
    }


  }
}


