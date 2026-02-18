package gov.cdc.nbs.search.redirect.simple;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import gov.cdc.nbs.testing.interaction.http.Authenticated;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.util.MultiValueMap;

@Component
class SimpleSearchRedirectRequester {

  private final Authenticated authenticated;
  private final MockMvc mvc;

  public SimpleSearchRedirectRequester(final Authenticated authenticated, final MockMvc mvc) {
    this.authenticated = authenticated;
    this.mvc = mvc;
  }

  ResultActions request(final MultiValueMap<String, String> parameters) {
    try {
      return mvc.perform(
          this.authenticated.withSession(
              post("/nbs/redirect/simpleSearch")
                  .params(parameters)
                  .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                  .accept(MediaType.ALL)));
    } catch (Exception exception) {
      throw new IllegalStateException(
          "Unable to execute Simple Search Redirect Request", exception);
    }
  }
}
