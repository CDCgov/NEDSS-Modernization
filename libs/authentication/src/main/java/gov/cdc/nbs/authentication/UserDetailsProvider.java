package gov.cdc.nbs.authentication;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class UserDetailsProvider {

  public NbsUserDetails getCurrentUserDetails() {
    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    if (auth != null) {
      return (NbsUserDetails) auth.getPrincipal();
    }
    return null;
  }
}
