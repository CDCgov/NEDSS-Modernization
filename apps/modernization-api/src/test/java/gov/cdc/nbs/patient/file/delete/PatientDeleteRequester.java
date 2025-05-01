package gov.cdc.nbs.patient.file.delete;

import gov.cdc.nbs.testing.interaction.http.Authenticated;
import org.springframework.stereotype.Component;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;

@Component
class PatientDeleteRequester {

  private final MockMvc mvc;
  private final Authenticated authenticated;

  PatientDeleteRequester(final MockMvc mvc, final Authenticated authenticated) {
    this.mvc = mvc;
    this.authenticated = authenticated;
  }

  ResultActions request(final long patient) {
    try {
      return mvc.perform(
          this.authenticated.withUser(
              delete("/nbs/api/patients/{patient}", patient)
          )
      );
    } catch (Exception exception) {
      throw new IllegalStateException(
          "An unexpected error occurred when deleting a patient.",
          exception
      );
    }
  }

}
