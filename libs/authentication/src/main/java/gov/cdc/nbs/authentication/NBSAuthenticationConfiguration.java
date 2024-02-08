package gov.cdc.nbs.authentication;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile("!oidc")
class NBSAuthenticationConfiguration {

  @Bean
  AuthenticationConfigurer nbsAuthenticationConfigurer(
      final IgnoredPaths ignoredPaths,
      final NBSAuthenticationFilterFactory nbsAuthenticationFilterFactory
  ) {
    return new NBSAuthenticationConfigurer(ignoredPaths, nbsAuthenticationFilterFactory);
  }

}
