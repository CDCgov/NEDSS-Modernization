package gov.cdc.nbs.patient.file;

import org.springframework.stereotype.Component;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import gov.cdc.nbs.testing.interaction.http.Authenticated;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@Component
class PatientHeaderRequester {

  private final MockMvc mvc;
  private final Authenticated authenticated;

  PatientHeaderRequester(final MockMvc mvc, final Authenticated authenticated) {
    this.mvc = mvc;
    this.authenticated = authenticated;
  }

  ResultActions request(long patientId) throws Exception {
    return mvc.perform(
        this.authenticated.withUser(
            get("/nbs/api/patient/" + String.valueOf(patientId) + "/file")))
        .andDo(print());
  }
}
