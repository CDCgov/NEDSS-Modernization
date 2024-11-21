package gov.cdc.nbs.patient;

import gov.cdc.nbs.patient.identifier.PatientIdentifier;
import gov.cdc.nbs.testing.interaction.http.Authenticated;
import org.springframework.stereotype.Component;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@Component
class NBS6PatientFileRequester {

  private final MockMvc mvc;
  private final Authenticated authenticated;

  NBS6PatientFileRequester(final MockMvc mvc, final Authenticated authenticated) {
    this.mvc = mvc;
    this.authenticated = authenticated;
  }

  ResultActions file(final PatientIdentifier patient) {
    try {
      return mvc.perform(
          authenticated.withSession(
              get(
                  "/nbs/api/patient/{patient}/file/redirect",
                  patient.id()
              )
          )
      );
    } catch (Exception e) {
      throw new IllegalStateException("Unexpected error when requesting a redirect to the NBS6 patient file", e);
    }
  }
}
