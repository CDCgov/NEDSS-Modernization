package gov.cdc.nbs.authentication;

import gov.cdc.nbs.authentication.session.SessionAuthenticator;
import gov.cdc.nbs.authentication.token.NBSTokenValidator;
import jakarta.servlet.Filter;
import org.springframework.stereotype.Component;

@Component
public class NBSAuthenticationFilterFactory {

  private final NBSTokenValidator tokenValidator;
  private final NBSAuthenticationIssuer authIssuer;
  private final SessionAuthenticator sessionAuthenticator;

  public NBSAuthenticationFilterFactory(
      final NBSTokenValidator tokenValidator,
      final NBSAuthenticationIssuer authIssuer,
      final SessionAuthenticator sessionAuthenticator) {
    this.tokenValidator = tokenValidator;
    this.authIssuer = authIssuer;
    this.sessionAuthenticator = sessionAuthenticator;
  }

  public Filter ignoring(final IgnoredPaths paths) {
    return new NBSAuthenticationFilter(tokenValidator, paths, authIssuer, sessionAuthenticator);
  }
}
