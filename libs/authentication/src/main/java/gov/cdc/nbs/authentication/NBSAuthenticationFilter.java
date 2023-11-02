package gov.cdc.nbs.authentication;

import java.io.IOException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;
import org.springframework.web.filter.OncePerRequestFilter;
import gov.cdc.nbs.authentication.config.SecurityProperties;
import gov.cdc.nbs.authentication.session.AuthorizedSessionResolver;
import gov.cdc.nbs.authentication.session.SessionAuthorization;
import gov.cdc.nbs.authentication.token.NBSTokenCookieEnsurer;
import gov.cdc.nbs.authentication.token.NBSTokenValidator;
import gov.cdc.nbs.authentication.token.NBSTokenValidator.TokenValidation;

/**
 * A {@code Filter} that ensures that incoming requests have a valid 'Authentication' header, nbs_token cookie, or
 * JSESSIONID. An unauthorized user will be redirected to `/nbs/timeout`/
 */
public class NBSAuthenticationFilter extends OncePerRequestFilter {
  private final NBSTokenValidator tokenValidator;
  private final AuthorizedSessionResolver sessionResolver;
  private final NBSTokenCookieEnsurer cookieEnsurer;
  private final SecurityProperties securityProperties;
  private final UserService userService;

  public NBSAuthenticationFilter(
      final NBSTokenValidator tokenValidator,
      final AuthorizedSessionResolver sessionResolver,
      final NBSTokenCookieEnsurer cookieEnsurer,
      final SecurityProperties securityProperties,
      final UserService userService) {
    this.tokenValidator = tokenValidator;
    this.sessionResolver = sessionResolver;
    this.cookieEnsurer = cookieEnsurer;
    this.securityProperties = securityProperties;
    this.userService = userService;
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
        applyCredentials(tokenValidation.user(), incoming, outgoing);
        chain.doFilter(incoming, outgoing);
        break;
      case EXPIRED, UNSET:
        // check if the JSESSIONID is valid
        doSessionAuthentication(incoming, outgoing, chain);
        break;
      case INVALID:
        // Redirect to timeout
        outgoing.setStatus(HttpStatus.FOUND.value());
        outgoing.setHeader(HttpHeaders.LOCATION, "/nbs/timeout");
        return;
    }
  }

  private void doSessionAuthentication(
      HttpServletRequest incoming,
      HttpServletResponse outgoing,
      FilterChain chain) throws IOException, ServletException {
    SessionAuthorization sessionAuthorization = sessionResolver.resolve(incoming);
    if (sessionAuthorization instanceof SessionAuthorization.Authorized authorized) {
      // JSESSIONID was valid. Create and apply token
      applyCredentials(authorized.user().user(), incoming, outgoing);
      chain.doFilter(incoming, outgoing);
    } else if (sessionAuthorization instanceof SessionAuthorization.Unauthorized unauthorized) {
      unauthorized.apply(securityProperties).accept(outgoing);
    }
  }

  // Fetches the user from the database and sets the Spring security context as well as adding the token to the response
  private void applyCredentials(
      String user,
      HttpServletRequest request,
      HttpServletResponse response) {
    NbsUserDetails userDetails = userService.loadUserByUsername(user);
    Authentication auth = createSpringAuthentication(userDetails, request);
    SecurityContextHolder.getContext().setAuthentication(auth);
    cookieEnsurer.ensure(user, response);
  }


  /**
   * Creates a {@link org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken} that
   * notifies Spring Security that the request has been authorized
   */
  private Authentication createSpringAuthentication(
      final NbsUserDetails principal,
      final HttpServletRequest request) {

    var authentication = new PreAuthenticatedAuthenticationToken(principal, null, principal.getAuthorities());
    authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
    return authentication;
  }

}
