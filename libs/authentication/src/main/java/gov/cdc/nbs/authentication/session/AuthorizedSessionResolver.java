package gov.cdc.nbs.authentication.session;

import gov.cdc.nbs.authentication.AuthorizedUserResolver;
import gov.cdc.nbs.authentication.SessionCookie;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Component;

@Component
class AuthorizedSessionResolver {
  private final AuthorizedUserResolver resolver;

  AuthorizedSessionResolver(final AuthorizedUserResolver resolver) {
    this.resolver = resolver;
  }

  SessionAuthorization resolve(final HttpServletRequest incoming) {
    return SessionCookie.resolve(incoming.getCookies())
        .flatMap(session -> resolver.resolve(session.identifier()).map(this::authorized))
        .orElse(new SessionAuthorization.Unauthorized());
  }

  private SessionAuthorization authorized(final String user) {
    return new SessionAuthorization.Authorized(user);
  }
}
