package gov.cdc.nbs.testing.interaction.http;

import gov.cdc.nbs.authentication.NBSToken;
import gov.cdc.nbs.authentication.SessionCookie;
import gov.cdc.nbs.testing.authorization.ActiveUser;
import gov.cdc.nbs.testing.support.Active;
import java.util.Optional;
import java.util.function.Supplier;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;
import org.springframework.stereotype.Component;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Component
public class Authenticated {

  private final Active<ActiveUser> activeUser;
  private final Active<SessionCookie> activeSession;
  private final UserDetailsService resolver;

  public Authenticated(
      final Active<ActiveUser> activeUser,
      final Active<SessionCookie> activeSession,
      final UserDetailsService resolver) {
    this.activeUser = activeUser;
    this.activeSession = activeSession;
    this.resolver = resolver;
  }

  public void reset() {
    SecurityContextHolder.getContext().setAuthentication(null);
  }

  private Optional<UserDetails> userDetails() {
    return this.activeUser
        .maybeActive()
        .map(ActiveUser::username)
        .map(resolver::loadUserByUsername);
  }

  private Optional<Authentication> authentication() {
    return userDetails()
        .map(
            details ->
                new PreAuthenticatedAuthenticationToken(details, null, details.getAuthorities()));
  }

  /**
   * Executes the given {@code action} ensuring that the {@link SecurityContextHolder} is configured
   * to be authenticated with the Active User.
   *
   * @param action The action to perform while authenticated.
   * @param <T> The type of the return value of the {@code action}
   * @return The result of the action
   */
  @Transactional(propagation = Propagation.REQUIRES_NEW)
  public <T> T perform(final Supplier<T> action) {

    authentication().ifPresent(SecurityContextHolder.getContext()::setAuthentication);

    try {
      return action.get();
    } finally {
      reset();
    }
  }

  public MockHttpServletRequestBuilder withUser(final MockHttpServletRequestBuilder builder) {
    String authorization =
        activeUser
            .maybeActive()
            .map(ActiveUser::token)
            .map(NBSToken::value)
            .map(token -> "Bearer " + token)
            .orElse("NOPE");

    return withSession(builder).header(HttpHeaders.AUTHORIZATION, authorization);
  }

  public MockHttpServletRequestBuilder withSession(final MockHttpServletRequestBuilder builder) {

    this.activeSession.maybeActive().map(SessionCookie::asCookie).ifPresent(builder::cookie);

    return builder;
  }
}
