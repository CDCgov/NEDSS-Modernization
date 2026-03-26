package gov.cdc.nbs.questionbank.valueset.concept;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;

import com.fasterxml.jackson.databind.ObjectMapper;
import gov.cdc.nbs.questionbank.valueset.request.UpdateConceptRequest;
import gov.cdc.nbs.testing.interaction.http.Authenticated;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

@Component
public class UpdateConceptRequester {
  private final MockMvc mvc;
  private final Authenticated authenticated;
  private final ObjectMapper mapper;

  UpdateConceptRequester(
      final Authenticated authenticated, final MockMvc mvc, final ObjectMapper mapper) {
    this.authenticated = authenticated;
    this.mvc = mvc;
    this.mapper = mapper;
  }

  ResultActions send(String valueset, String localCode, UpdateConceptRequest request)
      throws Exception {
    return mvc.perform(
        this.authenticated
            .withUser(put("/api/v1/valueset/{codeSetNm}/concepts/{localCode}", valueset, localCode))
            .content(mapper.writeValueAsBytes(request))
            .contentType(MediaType.APPLICATION_JSON));
  }
}
