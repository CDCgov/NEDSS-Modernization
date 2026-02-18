package gov.cdc.nbs.authentication.oidc;

import gov.cdc.nbs.authentication.NBSAuthenticationResolver;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;
import org.springframework.stereotype.Component;

@Component
class OIDCAuthenticationConverter implements Converter<Jwt, PreAuthenticatedAuthenticationToken> {

  private final NBSAuthenticationResolver resolver;

  OIDCAuthenticationConverter(final NBSAuthenticationResolver resolver) {
    this.resolver = resolver;
  }

  @Override
  public PreAuthenticatedAuthenticationToken convert(final Jwt source) {
    String username = source.getClaimAsString("preferred_username");
    return resolver.resolve(username);
  }
}
