package gov.cdc.nbs.patient.investigation;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

import gov.cdc.nbs.patient.identifier.PatientIdentifier;
import gov.cdc.nbs.testing.interaction.http.Authenticated;
import org.springframework.stereotype.Component;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

@Component
class PatientFileInvestigationsRequester {

  private final MockMvc mvc;
  private final Authenticated authenticated;

  PatientFileInvestigationsRequester(final MockMvc mvc, final Authenticated authenticated) {
    this.mvc = mvc;
    this.authenticated = authenticated;
  }

  ResultActions all(final PatientIdentifier patient) {
    try {
      return mvc.perform(
              this.authenticated.withUser(
                  get("/nbs/api/patient/{patient}/investigations", patient.id())))
          .andDo(print());
    } catch (Exception exception) {
      throw new IllegalStateException(
          "An unexpected error occurred when viewing the investigations for the Patient file.",
          exception);
    }
  }

  ResultActions open(final PatientIdentifier patient) {
    try {
      return mvc.perform(
              this.authenticated.withUser(
                  get("/nbs/api/patient/{patient}/investigations/open", patient.id())))
          .andDo(print());
    } catch (Exception exception) {
      throw new IllegalStateException(
          "An unexpected error occurred when viewing the open investigations for the Patient file.",
          exception);
    }
  }
}
