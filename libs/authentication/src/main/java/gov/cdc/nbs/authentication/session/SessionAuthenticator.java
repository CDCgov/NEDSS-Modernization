package gov.cdc.nbs.authentication.session;

import gov.cdc.nbs.authentication.NBSAuthenticationException;
import gov.cdc.nbs.authentication.NBSAuthenticationIssuer;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class SessionAuthenticator {
  private final AuthorizedSessionResolver sessionResolver;
  private final NBSAuthenticationIssuer authIssuer;

   SessionAuthenticator(
      final AuthorizedSessionResolver sessionResolver,
      final NBSAuthenticationIssuer authIssuer
  ) {
    this.sessionResolver = sessionResolver;
    this.authIssuer = authIssuer;
  }

  public void authenticate(
      HttpServletRequest incoming,
      HttpServletResponse outgoing
  ) {
    SessionAuthorization sessionAuthorization = sessionResolver.resolve(incoming);
    if (sessionAuthorization instanceof SessionAuthorization.Authorized authorized) {
      authIssuer.issue(authorized.user(), incoming, outgoing);
    } else if (sessionAuthorization instanceof SessionAuthorization.Unauthorized) {
      throw new NBSAuthenticationException();
    }
  }
}
