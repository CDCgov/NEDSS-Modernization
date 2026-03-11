package gov.cdc.nbs.authentication.oidc;

import static org.mockito.Mockito.*;

import gov.cdc.nbs.authentication.NBSAuthenticationResolver;
import org.junit.jupiter.api.Test;
import org.springframework.security.oauth2.jwt.Jwt;

class OIDCAuthenticationConverterTest {
  @Test
  void should_invoke_resolve_with_preferred_username() {
    NBSAuthenticationResolver resolver = mock(NBSAuthenticationResolver.class);

    OIDCAuthenticationConverter converter = new OIDCAuthenticationConverter(resolver);

    Jwt jwt = mock(Jwt.class);
    when(jwt.getClaimAsString("preferred_username")).thenReturn("username-value");

    converter.convert(jwt);

    verify(resolver).resolve("username-value");
  }
}
