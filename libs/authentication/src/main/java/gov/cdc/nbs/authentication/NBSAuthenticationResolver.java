package gov.cdc.nbs.authentication;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;
import org.springframework.stereotype.Component;

@Component
public class NBSAuthenticationResolver {

  private final UserDetailsService userService;

  public NBSAuthenticationResolver(final UserDetailsService userService) {
    this.userService = userService;
  }

  public PreAuthenticatedAuthenticationToken resolve(final String username) {
    UserDetails userDetails = userService.loadUserByUsername(username);

    return new PreAuthenticatedAuthenticationToken(userDetails, null, userDetails.getAuthorities());
  }
}
