package gov.cdc.nbs.authentication;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import gov.cdc.nbs.authentication.config.SecurityProperties;
import gov.cdc.nbs.authentication.token.NBSTokenCookieEnsurer;

@ExtendWith(MockitoExtension.class)
class NBSAuthenticationIssuerTest {

  @Mock
  private NBSTokenCookieEnsurer cookieEnsurer;

  @Mock
  private UserService userService;

  @Mock
  private SecurityProperties securityProperties;

  @InjectMocks
  private NBSAuthenticationIssuer issuer;

  @Test
  void issues_credentials() {
    // Given a valid user
    NbsUserDetails userDetails = NbsUserDetails.builder().username("user").build();
    when(userService.loadUserByUsername("user")).thenReturn(userDetails);

    // And a valid request and response
    HttpServletRequest request = Mockito.mock(HttpServletRequest.class);
    HttpServletResponse response = Mockito.mock(HttpServletResponse.class);
    ArgumentCaptor<Cookie> cookieMonster = ArgumentCaptor.forClass(Cookie.class);
    doNothing().when(response).addCookie(cookieMonster.capture());

    // When credentials are issued
    issuer.issue("user", request, response);

    // Then the nbs_token cookie is added
    verify(cookieEnsurer).ensure("user", response);

    // And the nbs_user cookie is added
    assertEquals("nbs_user", cookieMonster.getValue().getName());

    // And the spring security context is set
    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    NbsUserDetails details = (NbsUserDetails) auth.getPrincipal();
    assertEquals(userDetails, details);
    assertEquals("user", details.getUsername());
  }
}
