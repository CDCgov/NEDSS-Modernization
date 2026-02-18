package gov.cdc.nbs.patient.file.history;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

import gov.cdc.nbs.testing.interaction.http.Authenticated;
import org.springframework.stereotype.Component;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

@Component
class PatientMergeHistoryRequester {

  private final MockMvc mvc;
  private final Authenticated authenticated;

  PatientMergeHistoryRequester(final MockMvc mvc, final Authenticated authenticated) {
    this.mvc = mvc;
    this.authenticated = authenticated;
  }

  ResultActions request(final long patient) {
    try {
      return mvc.perform(
              this.authenticated.withUser(
                  get("/nbs/api/patients/{patient}/merge/history", patient)))
          .andDo(print());
    } catch (Exception exception) {
      throw new IllegalStateException(
          "An unexpected error occurred when viewing the patient file merge history.", exception);
    }
  }
}
