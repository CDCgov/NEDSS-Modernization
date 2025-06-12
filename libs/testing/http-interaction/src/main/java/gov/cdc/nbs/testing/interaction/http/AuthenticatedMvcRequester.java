package gov.cdc.nbs.testing.interaction.http;

import org.springframework.stereotype.Component;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

@Component
public class AuthenticatedMvcRequester {

  private final MockMvc mvc;
  private final Authenticated authenticated;

  public AuthenticatedMvcRequester(final MockMvc mvc, final Authenticated authenticated) {
    this.mvc = mvc;
    this.authenticated = authenticated;
  }

  public ResultActions request(final MockHttpServletRequestBuilder builder) {
    try {
      return mvc.perform(
          this.authenticated.withUser(
              builder
          )
      );
    } catch (Exception exception) {
      throw new IllegalStateException(
          "An unexpected error occurred when executing an authenticated request.",
          exception
      );
    }
  }
}
