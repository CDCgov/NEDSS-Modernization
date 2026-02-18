package gov.cdc.nbs.patient.file.edit;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;

import com.fasterxml.jackson.databind.ObjectMapper;
import gov.cdc.nbs.testing.interaction.http.AuthenticatedMvcRequester;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.test.web.servlet.ResultActions;

@Component
class PatientEditRequester {

  private final AuthenticatedMvcRequester authenticated;
  private final ObjectMapper mapper;

  PatientEditRequester(final AuthenticatedMvcRequester authenticated, final ObjectMapper mapper) {
    this.authenticated = authenticated;
    this.mapper = mapper;
  }

  ResultActions edit(final long identifier, final EditedPatient changes) {
    try {
      return this.authenticated.request(
          put("/nbs/api/patients/{patient}", identifier)
              .content(mapper.writeValueAsBytes(changes))
              .contentType(MediaType.APPLICATION_JSON));

    } catch (Exception exception) {
      throw new IllegalStateException(
          "An unexpected error occurred when creating a patient.", exception);
    }
  }
}
