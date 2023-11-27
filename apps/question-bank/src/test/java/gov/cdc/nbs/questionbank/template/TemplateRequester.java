package gov.cdc.nbs.questionbank.template;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import org.springframework.stereotype.Component;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import gov.cdc.nbs.testing.interaction.http.Authenticated;

@Component
class TemplateRequester {
  private final Authenticated authenticated;
  private final MockMvc mvc;

  TemplateRequester(
      final Authenticated authenticated,
      final MockMvc mvc) {
    this.authenticated = authenticated;
    this.mvc = mvc;
  }

  ResultActions getAll() throws Exception {
    MockHttpServletRequestBuilder builder = get("/api/v1/template/");
    return mvc.perform(this.authenticated.withUser(builder)).andDo(print());
  }

}

