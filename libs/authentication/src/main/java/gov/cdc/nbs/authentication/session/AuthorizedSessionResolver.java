package gov.cdc.nbs.authentication.session;

import javax.servlet.http.HttpServletRequest;
import gov.cdc.nbs.authentication.AuthorizedUserResolver;
import gov.cdc.nbs.authentication.NBSUserCookie;
import gov.cdc.nbs.authentication.SessionCookie;

public class AuthorizedSessionResolver {
  private final AuthorizedUserResolver resolver;

  AuthorizedSessionResolver(final AuthorizedUserResolver resolver) {
    this.resolver = resolver;
  }

  public SessionAuthorization resolve(final HttpServletRequest incoming) {
    return SessionCookie.resolve(incoming.getCookies())
        .flatMap(
            session -> resolver.resolve(session.identifier())
                .map(user -> authorized(session, new NBSUserCookie(user))))
        .orElse(new SessionAuthorization.Unauthorized());
  }

  private SessionAuthorization authorized(final SessionCookie session, final NBSUserCookie user) {
    return new SessionAuthorization.Authorized(session, user);
  }
}
