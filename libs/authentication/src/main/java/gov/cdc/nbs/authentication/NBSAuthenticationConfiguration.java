package gov.cdc.nbs.authentication;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(SecurityProperties.class)
class NBSAuthenticationConfiguration {

  @Bean
  @ConditionalOnProperty(
      name = "nbs.security.oidc.enabled",
      havingValue = "false",
      matchIfMissing = true)
  AuthenticationConfigurer nbsAuthenticationConfigurer(
      final IgnoredPaths ignoredPaths,
      final NBSAuthenticationFilterFactory nbsAuthenticationFilterFactory) {
    return new NBSAuthenticationConfigurer(ignoredPaths, nbsAuthenticationFilterFactory);
  }

  @Bean
  Algorithm jwtAlgorithm(final SecurityProperties properties) {
    // Per OWASP, key should be at least 64 characters
    // https://cheatsheetseries.owasp.org/cheatsheets/JSON_Web_Token_for_Java_Cheat_Sheet.html
    if (properties.tokenSecret() == null || properties.tokenSecret().length() < 64) {
      throw new IllegalArgumentException(
          "Invalid value specified for 'nbs.security.tokenSecret', Ensure the length of the secret is at least 64 characters");
    }
    return Algorithm.HMAC256(properties.tokenSecret());
  }

  @Bean
  JWTVerifier jwtVerifier(final Algorithm algorithm, final SecurityProperties properties) {
    return JWT.require(algorithm).withIssuer(properties.tokenIssuer()).build();
  }
}
