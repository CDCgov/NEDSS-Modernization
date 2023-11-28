package gov.cdc.nbs.questionbank.page.classic.redirect.incoming;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;


@ExtendWith(MockitoExtension.class)
class IncomingEditPageRedirectorTest {

  @InjectMocks
  private IncomingEditPageRedirector redirector;

  @Test
  void should_redirect_edit_page() {
    // Given a request with a valid page id cookie
    HttpServletRequest request = Mockito.mock(HttpServletRequest.class);
    when(request.getCookies()).thenReturn(new Cookie[] {new Cookie("Return-Page", "1234")});

    // When a request is processed return to the edit page 
    ResponseEntity<Void> response = redirector.returnToEdit(request);

    // Then a redirect is issued for the edit page
    String location = response.getHeaders().get("Location").get(0);
    assertEquals("/page-builder/edit/page/1234", location);
  }

  @Test
  void should_redirect_page_library() {
    // Given a request with no page id cookie
    HttpServletRequest request = Mockito.mock(HttpServletRequest.class);
    when(request.getCookies()).thenReturn(new Cookie[] {});

    // When a request is processed return to the edit page 
    ResponseEntity<Void> response = redirector.returnToEdit(request);

    // Then a redirect is issued for the edit page
    String location = response.getHeaders().get("Location").get(0);
    assertEquals("/page-builder/manage/pages", location);
  }
}
