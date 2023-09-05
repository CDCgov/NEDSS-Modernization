package gov.cdc.nbs.authentication;

import com.auth0.jwt.interfaces.DecodedJWT;
import gov.cdc.nbs.authentication.entity.AuthUser;
import gov.cdc.nbs.authentication.entity.AuthUserRepository;
import gov.cdc.nbs.exception.BadTokenException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static gov.cdc.nbs.authentication.NbsAuthorities.allowsAny;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {

    private final AuthUserRepository authUserRepository;
    private final UserAuthorizationVerifier verifier;
    private final TokenCreator tokenCreator;
    private final NBSUserDetailsResolver nbsUserDetailsResolver;

    @Override
    public NbsUserDetails loadUserByUsername(String username) {
        return authUserRepository
            .findByUserId(username)
            .map(authUser -> buildUserDetails(authUser, createToken(authUser)))
            .orElseThrow(() -> new UsernameNotFoundException("Username not found"));
    }

    public boolean isAuthorized(final long user, final String... permissions) {
        return this.verifier.isAuthorized(user, permissions);
    }

    public boolean isAuthorized(final NbsUserDetails userDetails, final String... permissions) {
        return userDetails.getAuthorities()
            .stream()
            .anyMatch(allowsAny(permissions));
    }

    /**
     * Lookup AuthUser in database using the JWT subject. Convert database entity to a new JWTUserDetails and return
     */
    @Transactional
    public NbsUserDetails findUserByToken(DecodedJWT jwt) {
        return authUserRepository
            .findByUserId(jwt.getSubject())
            .map(authUser -> buildUserDetails(authUser, createToken(authUser)))
            .orElseThrow(BadTokenException::new);
    }

    private NbsUserDetails buildUserDetails(AuthUser authUser, String token) {
        return nbsUserDetailsResolver.resolve(authUser, token);
    }

    private String createToken(AuthUser user) {
        return tokenCreator.forUser(user.getUserId());
    }



}
