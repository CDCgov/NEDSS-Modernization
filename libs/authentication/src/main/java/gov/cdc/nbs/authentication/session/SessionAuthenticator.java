package gov.cdc.nbs.authentication.session;

import java.io.IOException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import gov.cdc.nbs.authentication.NBSAuthenticationIssuer;
import gov.cdc.nbs.authentication.config.SecurityProperties;

@Component
public class SessionAuthenticator {
  private final AuthorizedSessionResolver sessionResolver;
  private final NBSAuthenticationIssuer authIssuer;
  private final SecurityProperties securityProperties;

  public SessionAuthenticator(
      final AuthorizedSessionResolver sessionResolver,
      final NBSAuthenticationIssuer authIssuer,
      final SecurityProperties securityProperties) {
    this.sessionResolver = sessionResolver;
    this.authIssuer = authIssuer;
    this.securityProperties = securityProperties;
  }

  public void authenticate(
      HttpServletRequest incoming,
      HttpServletResponse outgoing,
      FilterChain chain) throws IOException, ServletException {
    SessionAuthorization sessionAuthorization = sessionResolver.resolve(incoming);
    if (sessionAuthorization instanceof SessionAuthorization.Authorized authorized) {
      authIssuer.issue(authorized.user().user(), incoming, outgoing);
      chain.doFilter(incoming, outgoing);
    } else if (sessionAuthorization instanceof SessionAuthorization.Unauthorized unauthorized) {
      unauthorized.apply(securityProperties).accept(outgoing);
    }
  }
}
