package gov.cdc.nbs.authentication;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

public class NBSTokenTest {

  @Test
  void should_apply_token_with_security_when_null() {
    SecurityProperties properties = new SecurityProperties("secret", "test-issuer", 10000, null);
    HttpServletResponse response = mock(HttpServletResponse.class);

    NBSToken token = new NBSToken("token");
    token.apply(properties, response);

    ArgumentCaptor<Cookie> captor = ArgumentCaptor.forClass(Cookie.class);
    verify(response).addCookie(captor.capture());
    Cookie cookie = captor.getValue();

    assertTrue(cookie.getSecure());
  }

  @Test
  void should_apply_token_with_security_when_true() {
    SecurityProperties properties = new SecurityProperties("secret", "test-issuer", 10000, true);
    HttpServletResponse response = mock(HttpServletResponse.class);

    NBSToken token = new NBSToken("token");
    token.apply(properties, response);

    ArgumentCaptor<Cookie> captor = ArgumentCaptor.forClass(Cookie.class);
    verify(response).addCookie(captor.capture());
    Cookie cookie = captor.getValue();

    assertTrue(cookie.getSecure());
  }

  @Test
  void should_apply_token_without_security_when_false() {
    SecurityProperties properties = new SecurityProperties("secret", "test-issuer", 10000, false);
    HttpServletResponse response = mock(HttpServletResponse.class);

    NBSToken token = new NBSToken("token");
    token.apply(properties, response);

    ArgumentCaptor<Cookie> captor = ArgumentCaptor.forClass(Cookie.class);
    verify(response).addCookie(captor.capture());
    Cookie cookie = captor.getValue();

    assertFalse(cookie.getSecure());
  }
}
