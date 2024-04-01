package gov.cdc.nbs.gateway.security.oidc;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;

@Configuration
@ConditionalOnProperty(name = "nbs.security.oidc.enabled", havingValue = "true")
class OIDCAuthenticationConfiguration {

  @Bean
  SecurityWebFilterChain oidcAuthentication(final ServerHttpSecurity http) {
    return http
        .authorizeExchange(authorize -> authorize.anyExchange().authenticated())
        .oauth2Client(Customizer.withDefaults())
        .oauth2Login(Customizer.withDefaults())
        .csrf(ServerHttpSecurity.CsrfSpec::disable)
        .build();
  }

}
