package gov.cdc.nbs.authentication;

import gov.cdc.nbs.authentication.token.NBSTokenCookieEnsurer;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

// Responsible for applying user authentication to a request
@Component
public class NBSAuthenticationIssuer {

  private final NBSTokenCookieEnsurer cookieEnsurer;
  private final SecurityProperties securityProperties;
  private final NBSAuthenticationResolver resolver;

  public NBSAuthenticationIssuer(
      final NBSTokenCookieEnsurer cookieEnsurer,
      final SecurityProperties securityProperties,
      final NBSAuthenticationResolver resolver) {
    this.cookieEnsurer = cookieEnsurer;
    this.securityProperties = securityProperties;
    this.resolver = resolver;
  }

  // Fetches the user from the database and sets the Spring security context and adds the necessary
  // cookies
  public void issue(final String user, final HttpServletResponse response) {
    Authentication authentication = this.resolver.resolve(user);
    SecurityContextHolder.getContext().setAuthentication(authentication);
    cookieEnsurer.ensure(user, response);
    new NBSUserCookie(user).apply(securityProperties, response);
  }
}
