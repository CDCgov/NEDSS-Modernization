package gov.cdc.nbs.authentication.user;

import gov.cdc.nbs.authentication.NbsUserDetails;
import java.util.Optional;
import java.util.Set;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

@Component
class NBSUserDetailResolver {

  private final UserInformationFinder informationFinder;
  private final GrantedAuthorityFinder authorityFinder;

  NBSUserDetailResolver(
      final UserInformationFinder informationFinder, final GrantedAuthorityFinder authorityFinder) {
    this.informationFinder = informationFinder;
    this.authorityFinder = authorityFinder;
  }

  Optional<NbsUserDetails> resolve(final String username) {
    return informationFinder.find(username).map(this::asUserDetails);
  }

  private NbsUserDetails asUserDetails(final UserInformation information) {
    Set<GrantedAuthority> authorities = authorityFinder.find(information.identifier());

    return new NbsUserDetails(
        information.identifier(),
        information.username(),
        information.first(),
        information.last(),
        authorities,
        information.enabled());
  }
}
