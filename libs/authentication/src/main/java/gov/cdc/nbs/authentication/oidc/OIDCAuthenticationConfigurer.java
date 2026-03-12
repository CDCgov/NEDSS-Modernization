package gov.cdc.nbs.authentication.oidc;

import gov.cdc.nbs.authentication.AuthenticationConfigurer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;

class OIDCAuthenticationConfigurer implements AuthenticationConfigurer {

  private final OIDCAuthenticationConverter converter;

  OIDCAuthenticationConfigurer(final OIDCAuthenticationConverter converter) {
    this.converter = converter;
  }

  @Override
  public HttpSecurity configure(final HttpSecurity http) throws Exception {
    return http.oauth2ResourceServer(
        oauth -> oauth.jwt(jwt -> jwt.jwtAuthenticationConverter(converter)));
  }
}
