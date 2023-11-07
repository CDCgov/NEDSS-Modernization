package gov.cdc.nbs.redirect;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThrows;
import static org.mockito.Mockito.when;
import javax.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.web.servlet.view.RedirectView;
import gov.cdc.nbs.exception.RedirectionException;

class RedirectControllerTest {

  private RedirectController redirectController = new RedirectController();

  @Test
  void should_issue_redirect() {
    // Given a request with a redirect header
    HttpServletRequest request = Mockito.mock(HttpServletRequest.class);
    when(request.getHeader("NBS_REDIRECT")).thenReturn("/myUrl");

    // When the redirect is processed
    RedirectView redirect = redirectController.redirect(request);

    // Then the redirect is issued to the proper location
    assertNotNull(redirect);
    assertEquals("/myUrl", redirect.getUrl());
  }

  @Test
  void should_throw_redirect_exception_no_header() {
    // Given a request with a redirect header
    HttpServletRequest request = Mockito.mock(HttpServletRequest.class);
    when(request.getHeader("NBS_REDIRECT")).thenReturn(null);

    // When the redirect is processed
    // Then an exception is thrown
    assertThrows(RedirectionException.class, () -> redirectController.redirect(request));
  }
}
