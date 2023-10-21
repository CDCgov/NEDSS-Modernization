package gov.cdc.nbs.concept.rest;

import org.springframework.stereotype.Component;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@Component
class ConceptsRequest {

  private final MockMvc mvc;

  ConceptsRequest(final MockMvc mvc) {
    this.mvc = mvc;
  }

  ResultActions request(final String name) throws Exception {
    return mvc.perform(
        get("/nbs/api/concepts/{name}", name)
    );
  }
}
