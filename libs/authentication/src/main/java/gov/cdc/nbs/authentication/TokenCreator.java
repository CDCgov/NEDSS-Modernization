package gov.cdc.nbs.authentication;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import gov.cdc.nbs.authentication.config.SecurityProperties;
import gov.cdc.nbs.authentication.entity.AuthUser;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.Instant;

@Component
public class TokenCreator {

    private final Algorithm algorithm;
    private final SecurityProperties properties;

    public TokenCreator(final Algorithm algorithm, final SecurityProperties properties) {
        this.algorithm = algorithm;
        this.properties = properties;
    }

    public String forUser(final String username) {
        Instant now = Instant.now();
        Instant expiry = now.plus(Duration.ofMillis(properties.getTokenExpirationMillis()));
        return JWT
            .create()
            .withIssuer(properties.getTokenIssuer())
            .withIssuedAt(now)
            .withExpiresAt(expiry)
            .withSubject(username)
            .sign(algorithm);
    }
}
