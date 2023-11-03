package gov.cdc.nbs.authentication;

import java.io.IOException;
import java.util.Collection;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.filter.OncePerRequestFilter;
import gov.cdc.nbs.authentication.session.SessionAuthenticator;
import gov.cdc.nbs.authentication.token.NBSTokenValidator;
import gov.cdc.nbs.authentication.token.NBSTokenValidator.TokenValidation;

/**
 * A {@code OncePerRequestFilter} that ensures that incoming requests have a valid 'Authentication' header, nbs_token,
 * or JSESSIONID. An unauthorized user will be redirected to `/nbs/timeout`/
 */
public class NBSAuthenticationFilter extends OncePerRequestFilter {
  public interface IgnoredPaths {
    public Collection<String> get();
  }

  private final NBSTokenValidator tokenValidator;
  private final IgnoredPaths ignoredPaths;
  private final NBSAuthenticationIssuer authIssuer;
  private final SessionAuthenticator sessionAuthenticator;

  public NBSAuthenticationFilter(
      final NBSTokenValidator tokenValidator,
      final IgnoredPaths ignoredPaths,
      final NBSAuthenticationIssuer authIssuer,
      final SessionAuthenticator sessionAuthenticator) {
    this.tokenValidator = tokenValidator;
    this.ignoredPaths = ignoredPaths;
    this.authIssuer = authIssuer;
    this.sessionAuthenticator = sessionAuthenticator;
  }

  @Override
  protected void doFilterInternal(
      HttpServletRequest incoming,
      HttpServletResponse outgoing,
      FilterChain chain)
      throws ServletException, IOException {
    // Check for an existing NBS token
    TokenValidation tokenValidation = tokenValidator.validate(incoming);
    switch (tokenValidation.status()) {
      case VALID:
        // Set the Spring auth context for the user
        authIssuer.issue(tokenValidation.user(), incoming, outgoing);
        chain.doFilter(incoming, outgoing);
        break;
      case EXPIRED, UNSET:
        // check if the JSESSIONID is valid
        sessionAuthenticator.authenticate(incoming, outgoing, chain);
        break;
      case INVALID:
        // Redirect to timeout
        outgoing.setStatus(HttpStatus.FOUND.value());
        outgoing.setHeader(HttpHeaders.LOCATION, "/nbs/timeout");
        break;
    }
  }

  @Override
  protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
    String uri = request.getRequestURI();
    return ignoredPaths.get().stream()
        .anyMatch(p -> new AntPathRequestMatcher(p).matches(request)) || "/nbs/timeout".equals(uri);
  }

}
