package gov.cdc.nbs.config.security;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;

@Configuration
@EnableConfigurationProperties(SecurityProperties.class)
public class JWTSecurityConfig {

    @Bean
    public Algorithm jwtAlgorithm(SecurityProperties properties) {
        // Per OWASP, key should be at least 64 characters
        // https://cheatsheetseries.owasp.org/cheatsheets/JSON_Web_Token_for_Java_Cheat_Sheet.html
        if (properties.getTokenSecret() == null || properties.getTokenSecret().length() < 64) {
            throw new IllegalArgumentException(
                    "Invalid value specified for 'nbs.security.tokenSecret', Ensure the length of the secret is at least 64 characters");
        }
        return Algorithm.HMAC256(properties.getTokenSecret());
    }

    @Bean
    public JWTVerifier jwtVerifier(Algorithm algorithm, SecurityProperties properties) {
        return JWT.require(algorithm)
                .withIssuer(properties.getTokenIssuer())
                .build();
    }

}
