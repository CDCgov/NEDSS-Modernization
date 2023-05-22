package gov.cdc.nbs.authentication;

import java.time.Duration;
import java.time.Instant;
import java.util.Arrays;
import java.util.Objects;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
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

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {

    private final Algorithm algorithm;
    private final SecurityProperties properties;
    private final AuthUserRepository authUserRepository;
    private final UserPermissionFinder permissionFinder;

    @Override
    public NbsUserDetails loadUserByUsername(String username) {
        return authUserRepository
                .findByUserId(username)
                .map(authUser -> buildUserDetails(authUser, createToken(authUser)))
                .orElseThrow(() -> new UsernameNotFoundException("Username not found"));
    }

    public boolean isAuthorized(final long user, final String... permissions) {
        return authUserRepository.findById(user)
                // introduce query to bypass unnecessary mapping of entire object
                // should only need the authorities or even do the entire check in JPQL
                .map(
                        authUser -> permissionFinder.getUserPermissions(authUser)
                                .stream()
                                .anyMatch(allowsAny(permissions)))
                .orElse(false);
    }

    public boolean isAuthorized(final NbsUserDetails userDetails, final String... permissions) {
        return userDetails.getAuthorities()
                .stream()
                .anyMatch(allowsAny(permissions));
    }

    private Predicate<NbsAuthority> allows(final String permission) {
        return authority -> Objects.equals(authority.getAuthority(), permission);
    }

    private Predicate<NbsAuthority> allowsAny(final String... permissions) {
        return Arrays.stream(permissions)
                .map(this::allows)
                .reduce(ignored -> false, Predicate::or);
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
                .id(authUser.getId())
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
