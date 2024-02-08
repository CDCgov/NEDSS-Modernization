package gov.cdc.nbs.authentication;

import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;
import org.springframework.stereotype.Component;

@Component
public class NBSAuthenticationResolver {

  private final UserService userService;

  public NBSAuthenticationResolver(final UserService userService) {
    this.userService = userService;
  }

  public PreAuthenticatedAuthenticationToken resolve(final String username) {
    NbsUserDetails userDetails = userService.loadUserByUsername(username);

    return new PreAuthenticatedAuthenticationToken(
        userDetails,
        null,
        userDetails.getAuthorities()
    );
  }
}
