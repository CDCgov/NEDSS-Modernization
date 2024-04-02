package gov.cdc.nbs.gateway.security;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;

@Configuration
@ConditionalOnProperty(
    name = "nbs.security.oidc.enabled",
    havingValue = "false",
    matchIfMissing = true
)
class DefaultAuthenticationConfiguration {

  @Bean
  SecurityWebFilterChain defaultAuthentication(final ServerHttpSecurity http) {
    return http
        .authorizeExchange(authorize -> authorize.anyExchange().permitAll())
        .csrf(ServerHttpSecurity.CsrfSpec::disable)
        .build();
  }

}
