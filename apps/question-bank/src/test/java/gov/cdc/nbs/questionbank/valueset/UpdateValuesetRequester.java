package gov.cdc.nbs.questionbank.valueset;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;

import com.fasterxml.jackson.databind.ObjectMapper;
import gov.cdc.nbs.questionbank.valueset.request.UpdateValueSetRequest;
import gov.cdc.nbs.testing.interaction.http.Authenticated;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

@Component
public class UpdateValuesetRequester {
  private final MockMvc mvc;
  private final Authenticated authenticated;
  private final ObjectMapper mapper;

  UpdateValuesetRequester(
      final Authenticated authenticated, final MockMvc mvc, final ObjectMapper mapper) {
    this.authenticated = authenticated;
    this.mvc = mvc;
    this.mapper = mapper;
  }

  ResultActions send(final String valueset, final UpdateValueSetRequest request) throws Exception {
    return mvc.perform(
        authenticated
            .withUser(put("/api/v1/valueset/{valueset}", valueset))
            .content(mapper.writeValueAsBytes(request))
            .contentType(MediaType.APPLICATION_JSON));
  }
}
