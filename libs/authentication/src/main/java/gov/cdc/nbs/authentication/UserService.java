package gov.cdc.nbs.authentication;

import gov.cdc.nbs.authentication.entity.AuthUser;
import gov.cdc.nbs.authentication.entity.AuthUserRepository;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static gov.cdc.nbs.authentication.NbsAuthorities.allowsAny;

@Service
public class UserService implements UserDetailsService {

    private final AuthUserRepository authUserRepository;
    private final NBSUserDetailsResolver resolver;

    public UserService(
        final AuthUserRepository authUserRepository,
        final NBSUserDetailsResolver resolver
    ) {
        this.authUserRepository = authUserRepository;
        this.resolver = resolver;
    }

    @Override
    @Transactional
    public NbsUserDetails loadUserByUsername(String username) {
        return authUserRepository
            .findByUserId(username)
            .map(this::buildUserDetails)
            .orElseThrow(() -> new UsernameNotFoundException("Username not found"));
    }


    public boolean isAuthorized(final NbsUserDetails userDetails, final String... permissions) {
        return userDetails.getAuthorities()
            .stream()
            .anyMatch(allowsAny(permissions));
    }

    private NbsUserDetails buildUserDetails(AuthUser authUser) {
        return resolver.resolve(authUser);
    }


}
