package gov.cdc.nbs.questionbank.valueset.concept;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

import com.fasterxml.jackson.databind.ObjectMapper;
import gov.cdc.nbs.questionbank.valueset.request.UpdateConceptRequest;
import gov.cdc.nbs.testing.interaction.http.Authenticated;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

@Component
public class ConceptRequest {
  private final Authenticated authenticated;
  private final MockMvc mvc;
  private final ObjectMapper mapper;

  ConceptRequest(final Authenticated authenticated, final MockMvc mvc, final ObjectMapper mapper) {
    this.authenticated = authenticated;
    this.mvc = mvc;
    this.mapper = mapper;
  }

  public ResultActions updateConceptRequest(
      final String codeSetNm, final String conceptCode, UpdateConceptRequest request)
      throws Exception {
    return mvc.perform(
            this.authenticated
                .withUser(
                    put(
                        "/api/v1/valueset/{codeSetNm}/concepts/{conceptCode}",
                        codeSetNm,
                        conceptCode))
                .content(asJsonString(request))
                .contentType(MediaType.APPLICATION_JSON))
        .andDo(print());
  }

  private String asJsonString(final Object obj) throws Exception {
    return mapper.writeValueAsString(obj);
  }
}
