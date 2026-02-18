package gov.cdc.nbs.questionbank.page.information;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import gov.cdc.nbs.testing.interaction.http.Authenticated;
import org.springframework.stereotype.Component;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

@Component
class PageInformationRequester {

  private final Authenticated authenticated;
  private final MockMvc mvc;

  PageInformationRequester(final Authenticated authenticated, final MockMvc mvc) {
    this.authenticated = authenticated;
    this.mvc = mvc;
  }

  ResultActions request(final long page) {
    try {
      return mvc.perform(
          this.authenticated.withUser(get("/api/v1/pages/{page}/information", page)));
    } catch (Exception exception) {
      throw new IllegalStateException("Unable to execute Page Information Request", exception);
    }
  }
}
