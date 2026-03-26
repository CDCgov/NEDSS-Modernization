package gov.cdc.nbs.questionbank.template;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

import gov.cdc.nbs.testing.interaction.http.Authenticated;
import org.springframework.stereotype.Component;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

@Component
class TemplateRequester {
  private final Authenticated authenticated;
  private final MockMvc mvc;

  TemplateRequester(final Authenticated authenticated, final MockMvc mvc) {
    this.authenticated = authenticated;
    this.mvc = mvc;
  }

  ResultActions getAll() throws Exception {
    return mvc.perform(this.authenticated.withUser(get("/api/v1/template/"))).andDo(print());
  }

  ResultActions getAllInv() throws Exception {
    return mvc.perform(this.authenticated.withUser(get("/api/v1/template/?type=INV")))
        .andDo(print());
  }
}
