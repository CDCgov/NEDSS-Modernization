package gov.cdc.nbs.authentication;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import java.time.Clock;
import java.time.Duration;
import java.time.Instant;
import org.springframework.stereotype.Component;

@Component
public class TokenCreator {

  private final Clock clock;
  private final Algorithm algorithm;
  private final SecurityProperties properties;

  public TokenCreator(
      final Clock clock, final Algorithm algorithm, final SecurityProperties properties) {
    this.clock = clock;
    this.algorithm = algorithm;
    this.properties = properties;
  }

  public NBSToken forUser(final String username) {
    Instant now = Instant.now(clock);
    Instant expiry = now.plus(Duration.ofMillis(properties.tokenExpirationMillis()));
    String token =
        JWT.create()
            .withIssuer(properties.tokenIssuer())
            .withIssuedAt(now)
            .withExpiresAt(expiry)
            .withSubject(username)
            .sign(algorithm);
    return new NBSToken(token);
  }
}
