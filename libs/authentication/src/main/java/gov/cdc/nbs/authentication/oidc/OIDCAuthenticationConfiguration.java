package gov.cdc.nbs.authentication.oidc;

import gov.cdc.nbs.authentication.AuthenticationConfigurer;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConditionalOnProperty(name = "nbs.security.oidc.enabled", havingValue = "true")
class OIDCAuthenticationConfiguration {

  @Bean
  AuthenticationConfigurer oidcAuthenticationConfigurer(
      final OIDCAuthenticationConverter converter) {
    return new OIDCAuthenticationConfigurer(converter);
  }
}
