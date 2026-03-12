package gov.cdc.nbs.option.concept;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import org.springframework.stereotype.Component;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

@Component
class ConceptsRequest {

  private final MockMvc mvc;

  ConceptsRequest(final MockMvc mvc) {
    this.mvc = mvc;
  }

  ResultActions request(final String name) throws Exception {
    return mvc.perform(get("/nbs/api/options/concepts/{name}", name));
  }
}
