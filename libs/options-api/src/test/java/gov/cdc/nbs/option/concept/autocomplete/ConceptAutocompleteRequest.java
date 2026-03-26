package gov.cdc.nbs.option.concept.autocomplete;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import org.springframework.stereotype.Component;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

@Component
class ConceptAutocompleteRequest {

  private final MockMvc mvc;

  ConceptAutocompleteRequest(final MockMvc mvc) {
    this.mvc = mvc;
  }

  ResultActions complete(final String name, final String criteria) throws Exception {
    return mvc.perform(
        get("/nbs/api/options/concepts/{name}/search", name).param("criteria", criteria));
  }

  ResultActions complete(final String name, final String criteria, final int limit)
      throws Exception {
    return mvc.perform(
        get("/nbs/api/options/concepts/{name}/search", name)
            .param("criteria", criteria)
            .param("limit", String.valueOf(limit)));
  }
}
