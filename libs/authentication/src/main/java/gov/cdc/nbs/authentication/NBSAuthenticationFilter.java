package gov.cdc.nbs.authentication;

import gov.cdc.nbs.authentication.session.SessionAuthenticator;
import gov.cdc.nbs.authentication.token.NBSTokenValidator;
import gov.cdc.nbs.authentication.token.NBSTokenValidator.TokenValidation;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

/**
 * A {@code OncePerRequestFilter} that ensures that incoming requests have a valid 'Authentication'
 * header, nbs_token, or JSESSIONID. An unauthorized user will be redirected to `/nbs/timeout`/
 */
public class NBSAuthenticationFilter extends OncePerRequestFilter {

  private static final System.Logger LOGGER =
      System.getLogger(NBSAuthenticationFilter.class.getName());

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
      final HttpServletRequest incoming,
      final HttpServletResponse outgoing,
      final FilterChain chain)
      throws ServletException, IOException {
    // Check for an existing NBS token
    TokenValidation tokenValidation = tokenValidator.validate(incoming);
    LOGGER.log(
        System.Logger.Level.WARNING,
        "NBS auth check: path=%s tokenStatus=%s incomingCookies=%s"
            .formatted(
                incoming.getRequestURI(),
                tokenValidation.status(),
                incoming.getCookies() == null
                    ? "none"
                    : java.util.Arrays.stream(incoming.getCookies())
                        .map(jakarta.servlet.http.Cookie::getName)
                        .toList()));
    try {
      switch (tokenValidation.status()) {
        case VALID:
          // Set the Spring auth context for the user
          authIssuer.issue(tokenValidation.user(), outgoing);
          LOGGER.log(
              System.Logger.Level.WARNING,
              "NBS auth check: path=%s VALID token issued for user=%s"
                  .formatted(incoming.getRequestURI(), tokenValidation.user()));
          break;
        case EXPIRED, UNSET:
          // attempt authentication using the JSESSIONID
          sessionAuthenticator.authenticate(incoming, outgoing);
          LOGGER.log(
              System.Logger.Level.WARNING,
              "NBS auth check: path=%s session fallback succeeded, authentication=%s"
                  .formatted(
                      incoming.getRequestURI(),
                      SecurityContextHolder.getContext().getAuthentication()));
          break;
        case INVALID:
          throw new NBSAuthenticationException();
      }
    } catch (NBSAuthenticationException exception) {
      LOGGER.log(
          System.Logger.Level.WARNING,
          "NBS auth check failed: path=%s tokenStatus=%s reason=%s"
              .formatted(
                  incoming.getRequestURI(), tokenValidation.status(), exception.getMessage()));
      SecurityContextHolder.getContext().setAuthentication(null);
    } catch (RuntimeException unexpected) {
      // Diagnostic only - rethrown unchanged. Catches things like a failed user lookup
      // (e.g. UsernameNotFoundException) that would otherwise propagate silently.
      LOGGER.log(
          System.Logger.Level.WARNING,
          "NBS auth check threw unexpectedly: path=%s tokenStatus=%s exceptionType=%s message=%s"
              .formatted(
                  incoming.getRequestURI(),
                  tokenValidation.status(),
                  unexpected.getClass().getName(),
                  unexpected.getMessage()),
          unexpected);
      throw unexpected;
    } finally {
      chain.doFilter(incoming, outgoing);
    }
  }

  @Override
  protected boolean shouldNotFilter(final HttpServletRequest request) {
    return ignoredPaths.ignored(request);
  }
}
