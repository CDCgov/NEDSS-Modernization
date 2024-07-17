package gov.cdc.nbs.redirect;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;
import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.ResponseEntity;

class RedirectControllerTest {

  private DefaultRedirectionPath defaultPath = new DefaultRedirectionPath("/search");
  private RedirectController redirectController = new RedirectController(defaultPath);

  @Test
  void should_issue_redirect() {
    // Given a request with a redirect header
    HttpServletRequest request = Mockito.mock(HttpServletRequest.class);
    when(request.getHeader("Location")).thenReturn("/myUrl");

    // When the redirect is processed
    ResponseEntity<Void> redirect = redirectController.redirect(request);

    // Then the redirect is issued to the proper location
    assertNotNull(redirect);
    String location = redirect.getHeaders().get("Location").get(0);
    assertEquals("/myUrl", location);
  }

  @Test
  void should_throw_redirect_exception_no_header() {
    // Given a request with a redirect header
    HttpServletRequest request = Mockito.mock(HttpServletRequest.class);
    when(request.getHeader("Location")).thenReturn(null);

    // When the redirect is processed
    ResponseEntity<Void> redirect = redirectController.redirect(request);

    // Then the redirect is issued to the default location
    assertNotNull(redirect);
    String location = redirect.getHeaders().get("Location").get(0);
    assertEquals("/search", location);
  }
}
