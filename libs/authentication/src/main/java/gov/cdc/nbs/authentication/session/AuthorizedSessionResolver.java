package gov.cdc.nbs.authentication.session;

import gov.cdc.nbs.authentication.AuthorizedUserResolver;
import gov.cdc.nbs.authentication.SessionCookie;
import jakarta.servlet.http.HttpServletRequest;
import java.util.Optional;
import org.springframework.stereotype.Component;

@Component
class AuthorizedSessionResolver {

  private static final System.Logger LOGGER =
      System.getLogger(AuthorizedSessionResolver.class.getName());

  private final AuthorizedUserResolver resolver;

  AuthorizedSessionResolver(final AuthorizedUserResolver resolver) {
    this.resolver = resolver;
  }

  SessionAuthorization resolve(final HttpServletRequest incoming) {
    var sessionCookie = SessionCookie.resolve(incoming.getCookies());
    if (sessionCookie.isEmpty()) {
      LOGGER.log(
          System.Logger.Level.WARNING, "Session auth fallback: no JSESSIONID cookie present");
      return new SessionAuthorization.Unauthorized();
    }

    LOGGER.log(
        System.Logger.Level.WARNING,
        "Session auth fallback: checking session identifier=%s"
            .formatted(sessionCookie.get().identifier()));

    return sessionCookie
        .flatMap(session -> resolver.resolve(session.identifier()).map(this::authorized))
        .map(
            authorization -> {
              LOGGER.log(
                  System.Logger.Level.WARNING,
                  "Session auth fallback: authorized as %s".formatted(authorization));
              return authorization;
            })
        .or(
            () -> {
              LOGGER.log(
                  System.Logger.Level.WARNING,
                  "Session auth fallback: JSESSIONID present but no authorized session found"
                      + " (session not in store, expired, or not yet replicated)");
              return Optional.empty();
            })
        .orElse(new SessionAuthorization.Unauthorized());
  }

  private SessionAuthorization authorized(final String user) {
    return new SessionAuthorization.Authorized(user);
  }
}
