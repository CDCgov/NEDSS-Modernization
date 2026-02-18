package gov.cdc.nbs.questionbank.valueset.concept;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import com.fasterxml.jackson.databind.ObjectMapper;
import gov.cdc.nbs.questionbank.valueset.request.CreateConceptRequest;
import gov.cdc.nbs.testing.interaction.http.Authenticated;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

@Component
public class CreateConceptRequester {

  private final MockMvc mvc;
  private final Authenticated authenticated;
  private final ObjectMapper mapper;

  CreateConceptRequester(
      final Authenticated authenticated, final MockMvc mvc, final ObjectMapper mapper) {
    this.authenticated = authenticated;
    this.mvc = mvc;
    this.mapper = mapper;
  }

  ResultActions create(String valueset, CreateConceptRequest request) throws Exception {
    return mvc.perform(
        this.authenticated
            .withUser(post("/api/v1/valueset/{codeSetNm}/concepts", valueset))
            .content(mapper.writeValueAsBytes(request))
            .contentType(MediaType.APPLICATION_JSON));
  }
}
