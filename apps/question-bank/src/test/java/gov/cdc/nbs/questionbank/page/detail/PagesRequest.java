package gov.cdc.nbs.questionbank.page.detail;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import gov.cdc.nbs.testing.interaction.http.Authenticated;
import org.springframework.stereotype.Component;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

@Component
class PagesRequest {

  private final Authenticated authenticated;
  private final MockMvc mvc;

  PagesRequest(final Authenticated authenticated, final MockMvc mvc) {
    this.authenticated = authenticated;
    this.mvc = mvc;
  }

  ResultActions request(final long page) throws Exception {
    return mvc.perform(this.authenticated.withUser(get("/api/v1/pages/{page}", page)));
  }
}
