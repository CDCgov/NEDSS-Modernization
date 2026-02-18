package gov.cdc.nbs.questionbank.valueset.concept;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import gov.cdc.nbs.testing.interaction.http.Authenticated;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

@Component
public class ConceptSearchRequester {
  private final MockMvc mvc;
  private final Authenticated authenticated;

  ConceptSearchRequester(final Authenticated authenticated, final MockMvc mvc) {
    this.authenticated = authenticated;
    this.mvc = mvc;
  }

  ResultActions search(String codeset, Pageable pageable) throws Exception {

    MockHttpServletRequestBuilder builder =
        post("/api/v1/valueset/{codeSetNm}/concepts/search", codeset);

    if (pageable != null) {
      builder.param("sort", pageable.getSort().toString().replace(": ", ","));
    }
    return mvc.perform(this.authenticated.withUser(builder));
  }
}
