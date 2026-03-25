package gov.cdc.nbs.patient.file;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import gov.cdc.nbs.patient.identifier.PatientIdentifier;
import gov.cdc.nbs.testing.interaction.http.Authenticated;
import org.springframework.stereotype.Component;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

@Component
class PatientFileRequester {

  private final MockMvc mvc;
  private final Authenticated authenticated;

  PatientFileRequester(final MockMvc mvc, final Authenticated authenticated) {
    this.mvc = mvc;
    this.authenticated = authenticated;
  }

  ResultActions request(final PatientIdentifier patient) {
    return request(patient.shortId());
  }

  ResultActions request(final long patient) {
    try {
      return mvc.perform(
          this.authenticated.withUser(get("/nbs/api/patients/{patient}/file", patient)));
    } catch (Exception exception) {
      throw new IllegalStateException(
          "An unexpected error occurred when resolving a Patient file.", exception);
    }
  }
}
