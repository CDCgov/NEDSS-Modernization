package gov.cdc.nbs.authentication;

import gov.cdc.nbs.authentication.entity.AuthUserRepository;
import org.springframework.stereotype.Component;

import static gov.cdc.nbs.authentication.NbsAuthorities.allowsAny;

@Component
public class UserAuthorizationVerifier {

    private final AuthUserRepository repository;
    private final UserPermissionFinder finder;

    public UserAuthorizationVerifier(
        final AuthUserRepository repository,
        final UserPermissionFinder finder
    ) {
        this.repository = repository;
        this.finder = finder;
    }

    public boolean isAuthorized(final long user, final String... permissions) {
        return repository.findById(user)
            // introduce query to bypass unnecessary mapping of entire object
            // should only need the authorities or even do the entire check in JPQL
            .map(
                authUser -> finder.getUserPermissions(authUser)
                    .stream()
                    .anyMatch(allowsAny(permissions)))
            .orElse(false);
    }
}
