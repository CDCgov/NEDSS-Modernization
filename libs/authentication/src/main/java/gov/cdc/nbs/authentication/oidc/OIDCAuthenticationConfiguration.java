package gov.cdc.nbs.authentication.oidc;

import gov.cdc.nbs.authentication.AuthenticationConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile("oidc")
class OIDCAuthenticationConfiguration {

  @Bean
  AuthenticationConfigurer oidcAuthenticationConfigurer(
      final OIDCAuthenticationConverter converter
  ) {
    return new OIDCAuthenticationConfigurer(converter);
  }

}
