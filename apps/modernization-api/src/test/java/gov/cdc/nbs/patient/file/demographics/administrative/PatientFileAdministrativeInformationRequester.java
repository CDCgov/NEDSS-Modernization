package gov.cdc.nbs.patient.file.demographics.administrative;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

import gov.cdc.nbs.testing.interaction.http.Authenticated;
import org.springframework.stereotype.Component;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

@Component
class PatientFileAdministrativeInformationRequester {

  private final MockMvc mvc;
  private final Authenticated authenticated;

  PatientFileAdministrativeInformationRequester(
      final MockMvc mvc, final Authenticated authenticated) {
    this.mvc = mvc;
    this.authenticated = authenticated;
  }

  ResultActions request(final long patient) {
    try {
      return mvc.perform(
              this.authenticated.withUser(
                  get("/nbs/api/patients/{patient}/demographics/administrative", patient)))
          .andDo(print());
    } catch (Exception exception) {
      throw new IllegalStateException(
          "An unexpected error occurred when viewing the administrative patient demographics.",
          exception);
    }
  }
}
