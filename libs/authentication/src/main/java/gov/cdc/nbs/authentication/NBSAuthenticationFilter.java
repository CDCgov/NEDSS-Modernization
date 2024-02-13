package gov.cdc.nbs.authentication;

import gov.cdc.nbs.authentication.session.SessionAuthenticator;
import gov.cdc.nbs.authentication.token.NBSTokenValidator;
import gov.cdc.nbs.authentication.token.NBSTokenValidator.TokenValidation;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * A {@code OncePerRequestFilter} that ensures that incoming requests have a valid 'Authentication' header, nbs_token,
 * or JSESSIONID. An unauthorized user will be redirected to `/nbs/timeout`/
 */
public class NBSAuthenticationFilter extends OncePerRequestFilter {


  private final NBSTokenValidator tokenValidator;
  private final IgnoredPaths ignoredPaths;
  private final NBSAuthenticationIssuer authIssuer;
  private final SessionAuthenticator sessionAuthenticator;
  private static final AntPathRequestMatcher timeoutPath = new AntPathRequestMatcher("/nbs/timeout");

  public NBSAuthenticationFilter(
      final NBSTokenValidator tokenValidator,
      final IgnoredPaths ignoredPaths,
      final NBSAuthenticationIssuer authIssuer,
      final SessionAuthenticator sessionAuthenticator
  ) {
    this.tokenValidator = tokenValidator;
    this.ignoredPaths = ignoredPaths;
    this.authIssuer = authIssuer;
    this.sessionAuthenticator = sessionAuthenticator;
  }

  @Override
  protected void doFilterInternal(
      final HttpServletRequest incoming,
      final HttpServletResponse outgoing,
      final FilterChain chain
  )
      throws ServletException, IOException {
    // Check for an existing NBS token
    TokenValidation tokenValidation = tokenValidator.validate(incoming);
    try {
      switch (tokenValidation.status()) {
        case VALID:
          // Set the Spring auth context for the user
          authIssuer.issue(tokenValidation.user(), incoming, outgoing);
          break;
        case EXPIRED, UNSET:
          // attempt authentication using the JSESSIONID
          sessionAuthenticator.authenticate(incoming, outgoing);
          break;
        case INVALID:
          throw new NBSAuthenticationException();
      }
    } catch (NBSAuthenticationException exception) {
      SecurityContextHolder.getContext().setAuthentication(null);
    } finally {
      chain.doFilter(incoming, outgoing);
    }

  }

  @Override
  protected boolean shouldNotFilter(final HttpServletRequest request) {
    return timeoutPath.matches(request) || ignoredPaths.ignored(request);
  }

}
