package gov.cdc.nbs.questionbank.page.classic.redirect.incoming;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

import gov.cdc.nbs.authentication.NbsUserDetails;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

@ExtendWith(MockitoExtension.class)
class IncomingEditPageRedirectorTest {

  @Mock private RecentlyCreatedPageFinder finder;

  @InjectMocks private IncomingEditPageRedirector redirector;

  @Test
  void should_redirect_edit_page() {
    // Given a user
    NbsUserDetails details = Mockito.mock(NbsUserDetails.class);
    when(details.getId()).thenReturn(1l);

    // and a request with a valid page id cookie
    HttpServletRequest request = Mockito.mock(HttpServletRequest.class);
    when(request.getCookies()).thenReturn(new Cookie[] {new Cookie("Return-Page", "1234")});

    // When a request is processed return to the edit page
    ResponseEntity<Void> response = redirector.returnToEdit(request, details);

    // Then a redirect is issued for the edit page
    String location = response.getHeaders().get("Location").get(0);
    assertEquals("/page-builder/pages/1234/edit", location);

    verifyNoInteractions(finder);
  }

  @Test
  void should_redirect_page_library() {
    // Given a user
    NbsUserDetails details = Mockito.mock(NbsUserDetails.class);
    when(details.getId()).thenReturn(1l);

    // and a request with no page id cookie
    HttpServletRequest request = Mockito.mock(HttpServletRequest.class);
    when(request.getCookies()).thenReturn(new Cookie[] {});

    // and no recently created pages
    when(finder.findRecentlyCreatedBy(1l)).thenReturn(Optional.empty());

    // When a request is processed return to the edit page
    ResponseEntity<Void> response = redirector.returnToEdit(request, details);

    // Then a redirect is issued for the library page
    String location = response.getHeaders().get("Location").get(0);
    assertEquals("/page-builder/pages", location);
  }

  @Test
  void should_redirect_recently_created() {
    // Given a user
    NbsUserDetails details = Mockito.mock(NbsUserDetails.class);
    when(details.getId()).thenReturn(1l);

    // and a request with no page id cookie
    HttpServletRequest request = Mockito.mock(HttpServletRequest.class);
    when(request.getCookies()).thenReturn(new Cookie[] {});

    // and a recently created pages
    when(finder.findRecentlyCreatedBy(1l)).thenReturn(Optional.of(2l));

    // When a request is processed return to the edit page
    ResponseEntity<Void> response = redirector.returnToEdit(request, details);

    // Then a redirect is issued for the edit page
    String location = response.getHeaders().get("Location").get(0);
    assertEquals("/page-builder/pages/2/edit", location);
  }
}
