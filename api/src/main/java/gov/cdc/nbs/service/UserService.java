package gov.cdc.nbs.service;

import java.time.Duration;
import java.time.Instant;

import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;

import gov.cdc.nbs.config.security.NbsUserDetails;
import gov.cdc.nbs.config.security.SecurityProperties;
import gov.cdc.nbs.entity.odse.AuthUser;
import gov.cdc.nbs.exception.BadTokenException;
import gov.cdc.nbs.repository.AuthUserRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {
    private final Algorithm algorithm;
    private final SecurityProperties properties;
    private final AuthUserRepository authUserRepository;

    @Override
    public NbsUserDetails loadUserByUsername(String username) {
        return authUserRepository
                .findByUserId(username)
                .map(authUser -> buildUserDetails(authUser, createToken(authUser)))
                .orElseThrow(() -> new UsernameNotFoundException("Username not found"));
    }

    /*
     * Lookup AuthUser in database using the JWT subject. Convert database entity to
     * a new JWTUserDetails and return
     */
    public NbsUserDetails findUserByToken(DecodedJWT jwt) {
        return authUserRepository
                .findByUserId(jwt.getSubject())
                .map(authUser -> buildUserDetails(authUser, createToken(authUser)))
                .orElseThrow(BadTokenException::new);
    }

    private NbsUserDetails buildUserDetails(AuthUser authUser, String token) {
        return NbsUserDetails
                .builder()
                .username(authUser.getUserId())
                .password(null)
                .authorities(null)
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
