package gov.cdc.nbs.gateway.security.oidc;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.net.URI;
import java.net.URISyntaxException;

@Configuration
@ConditionalOnProperty(name = "nbs.security.oidc.enabled", havingValue = "true")
class AuthenticationServiceProvider {

  @Bean
  AuthenticationService authenticationService(
      @Value("${nbs.security.oidc.host}") final String host,
      @Value("${nbs.security.oidc.base}") final String base
  ) throws URISyntaxException {
    URI uri = new URI(host);

    return new AuthenticationService(uri, base);
  }
}
