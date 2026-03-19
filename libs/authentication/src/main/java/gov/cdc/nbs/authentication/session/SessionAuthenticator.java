package gov.cdc.nbs.authentication.session;

import gov.cdc.nbs.authentication.NBSAuthenticationException;
import gov.cdc.nbs.authentication.NBSAuthenticationIssuer;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;

@Component
public class SessionAuthenticator {
  private final AuthorizedSessionResolver sessionResolver;
  private final NBSAuthenticationIssuer authIssuer;

  SessionAuthenticator(
      final AuthorizedSessionResolver sessionResolver, final NBSAuthenticationIssuer authIssuer) {
    this.sessionResolver = sessionResolver;
    this.authIssuer = authIssuer;
  }

  public void authenticate(HttpServletRequest incoming, HttpServletResponse outgoing) {
    SessionAuthorization sessionAuthorization = sessionResolver.resolve(incoming);
    if (sessionAuthorization instanceof SessionAuthorization.Authorized(String user)) {
      authIssuer.issue(user, outgoing);
    } else if (sessionAuthorization instanceof SessionAuthorization.Unauthorized) {
      throw new NBSAuthenticationException();
    }
  }
}
