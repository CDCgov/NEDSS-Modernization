package gov.cdc.nbs.questionbank.page.classic.preview;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import gov.cdc.nbs.questionbank.page.classic.ClassicPreviewPagePreparer;

@ExtendWith(MockitoExtension.class)
class ClassicPagePreviewRedirectorTest {

  @Mock
  private ClassicPreviewPagePreparer preparer;

  @InjectMocks
  private ClassicPagePreviewRedirector redirector;

  @Test
  void should_redirect_to_preview() {
    // When a request is processed to preview 
    ResponseEntity<Void> response = redirector.view(123L);

    // Then classic is prepared
    verify(preparer).prepare();

    // And a redirect is issued to classic
    String location = response.getHeaders().get("Location").get(0);
    assertEquals("/nbs/PreviewPage.do?from=L&method=viewPageLoad&waTemplateUid=123", location);

    // And a Return-Page cookie is set
    String setCookie = response.getHeaders().get("Set-Cookie").get(0);
    assertEquals("Return-Page=123; Path=/nbs; Secure; HttpOnly; SameSite=Strict", setCookie);
  }

}
