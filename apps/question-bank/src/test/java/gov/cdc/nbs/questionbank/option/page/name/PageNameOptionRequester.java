package gov.cdc.nbs.questionbank.option.page.name;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

import gov.cdc.nbs.testing.interaction.http.Authenticated;
import org.springframework.stereotype.Component;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

@Component
class PageNameOptionRequester {

  private final Authenticated authenticated;
  private final MockMvc mvc;

  PageNameOptionRequester(final Authenticated authenticated, final MockMvc mvc) {
    this.authenticated = authenticated;
    this.mvc = mvc;
  }

  ResultActions request() {
    try {
      return mvc.perform(this.authenticated.withUser(get("/api/v1/options/page/names")))
          .andDo(print());
    } catch (Exception exception) {
      throw new IllegalStateException("Unable to execute Page name options Request", exception);
    }
  }
}
