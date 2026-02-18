package gov.cdc.nbs.patient.profile.create;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

import com.fasterxml.jackson.databind.ObjectMapper;
import gov.cdc.nbs.testing.interaction.http.Authenticated;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

@Component
public class PatientCreateRequester {

  private final MockMvc mvc;
  private final Authenticated authenticated;
  private final ObjectMapper mapper;

  public PatientCreateRequester(
      final MockMvc mvc, final Authenticated authenticated, final ObjectMapper mapper) {
    this.mvc = mvc;
    this.authenticated = authenticated;
    this.mapper = mapper;
  }

  public ResultActions create(final NewPatient patient) {
    try {
      return mvc.perform(
              this.authenticated.withUser(
                  post("/nbs/api/profile")
                      .content(mapper.writeValueAsBytes(patient))
                      .contentType(MediaType.APPLICATION_JSON)))
          .andDo(print());
    } catch (Exception exception) {
      throw new IllegalStateException(
          "An unexpected error occurred when creating a patient.", exception);
    }
  }
}
