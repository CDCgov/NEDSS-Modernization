package gov.cdc.nbs.authentication;

import gov.cdc.nbs.authentication.config.SecurityProperties;
import gov.cdc.nbs.authentication.token.NBSTokenCookieEnsurer;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

// Responsible for applying user authentication to a request
@Component
public class NBSAuthenticationIssuer {

  private final NBSTokenCookieEnsurer cookieEnsurer;
  private final UserService userService;
  private final SecurityProperties securityProperties;

  public NBSAuthenticationIssuer(
      final NBSTokenCookieEnsurer cookieEnsurer,
      final UserService userService,
      final SecurityProperties securityProperties
  ) {
    this.cookieEnsurer = cookieEnsurer;
    this.userService = userService;
    this.securityProperties = securityProperties;
  }


  // Fetches the user from the database and sets the Spring security context and adds the necessary cookies
  public void issue(
      final String user,
      final HttpServletRequest request,
      final HttpServletResponse response
  ) {
    NbsUserDetails userDetails = userService.loadUserByUsername(user);
    Authentication authentication = createSpringAuthentication(userDetails, request);
    SecurityContextHolder.getContext().setAuthentication(authentication);
    cookieEnsurer.ensure(user, response);
    new NBSUserCookie(user).apply(securityProperties, response);
  }

  /**
   * Creates a {@link org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken} that
   * notifies Spring Security that the request has been authorized
   */
  private Authentication createSpringAuthentication(
      final NbsUserDetails principal,
      final HttpServletRequest request
  ) {

    var authentication = new PreAuthenticatedAuthenticationToken(principal, null, principal.getAuthorities());
    authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
    return authentication;
  }
}
