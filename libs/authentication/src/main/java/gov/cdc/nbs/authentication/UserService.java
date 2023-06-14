package gov.cdc.nbs.authentication;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import gov.cdc.nbs.authentication.config.SecurityProperties;
import gov.cdc.nbs.authentication.entity.AuthProgAreaAdmin;
import gov.cdc.nbs.authentication.entity.AuthUser;
import gov.cdc.nbs.authentication.entity.AuthUserRepository;
import gov.cdc.nbs.authentication.enums.AuthRecordStatus;
import gov.cdc.nbs.exception.BadTokenException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.time.Instant;
import java.util.stream.Collectors;

import static gov.cdc.nbs.authentication.NbsAuthorities.allowsAny;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {

    private final Algorithm algorithm;
    private final SecurityProperties properties;
    private final AuthUserRepository authUserRepository;
    private final UserPermissionFinder permissionFinder;
    private final UserAuthorizationVerifier verifier;

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
        return NbsUserDetails
                .builder()
                .id(authUser.getNedssEntryId())
                .firstName(authUser.getUserFirstNm())
                .lastName(authUser.getUserLastNm())
                .isMasterSecurityAdmin(authUser.getMasterSecAdminInd().equals('T'))
                .isProgramAreaAdmin(authUser.getProgAreaAdminInd().equals('T'))
                .adminProgramAreas(authUser.getAdminProgramAreas()
                        .stream()
                        .map(AuthProgAreaAdmin::getProgAreaCd)
                        .collect(Collectors.toSet()))
                .username(authUser.getUserId())
                .password(null)
                .authorities(permissionFinder.getUserPermissions(authUser))
                .isEnabled(authUser.getAudit().getRecordStatusCd().equals(AuthRecordStatus.ACTIVE))
                .token(token)
                .build();
    }

    private String createToken(AuthUser user) {
        Instant now = Instant.now();
        Instant expiry = Instant.now().plus(Duration.ofMillis(properties.getTokenExpirationMillis()));
        return JWT
                .create()
                .withIssuer(properties.getTokenIssuer())
                .withIssuedAt(now)
                .withExpiresAt(expiry)
                .withSubject(user.getUserId())
                .sign(algorithm);
    }



}
