package gov.cdc.nbs.questionbank.page.classic.edit;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;

import gov.cdc.nbs.questionbank.page.classic.ClassicEditPagePreparer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

@ExtendWith(MockitoExtension.class)
class ClassicPageEditRedirectorTest {

  @Mock private ClassicEditPagePreparer preparer;

  @InjectMocks private ClassicPageEditRedirector redirector;

  @Test
  void should_redirect_to_edit() {
    // When a request is processed to preview
    ResponseEntity<Void> response = redirector.edit(123L);

    // Then classic is prepared
    verify(preparer).prepare(123L);

    // And a redirect is issued to classic
    String location = response.getHeaders().get("Location").get(0);
    assertEquals(
        "/nbs/ManagePage.do?fromWhere=V&method=editPageContentsLoad&waTemplateUid=123", location);
  }
}
