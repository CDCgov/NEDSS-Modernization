package gov.cdc.nbs.authentication.user;

import gov.cdc.nbs.authentication.NbsUserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
class UserService implements UserDetailsService {

  private final NBSUserDetailResolver resolver;

  UserService(final NBSUserDetailResolver resolver) {
    this.resolver = resolver;
  }

  @Override
  @Transactional
  public NbsUserDetails loadUserByUsername(final String username) {
    return resolver
        .resolve(username)
        .orElseThrow(() -> new UsernameNotFoundException("Username not found"));
  }
}
