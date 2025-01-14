package gov.cdc.nbs.patient.search.home.redirect;

import gov.cdc.nbs.testing.interaction.http.Authenticated;
import org.springframework.stereotype.Component;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@Component
public class AdvancedSearchRedirectRequester {

  private final Authenticated authenticated;
  private final MockMvc mvc;

  public AdvancedSearchRedirectRequester(
      final Authenticated authenticated,
      final MockMvc mvc
  ) {
    this.authenticated = authenticated;
    this.mvc = mvc;
  }

  ResultActions request() {
    try {

      return mvc.perform(
          this.authenticated.withSession(
              get("/nbs/redirect/advancedSearch")
          )
      ).andDo(print());
    } catch (Exception exception) {
      throw new IllegalStateException("Unable to execute Advance Search Redirect Request", exception);
    }
  }

}
