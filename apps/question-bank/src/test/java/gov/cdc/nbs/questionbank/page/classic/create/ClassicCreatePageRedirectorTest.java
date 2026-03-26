package gov.cdc.nbs.questionbank.page.classic.create;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

@ExtendWith(MockitoExtension.class)
class ClassicCreatePageRedirectorTest {

  @Mock private ClassicCreatePagePreparer preparer;

  @InjectMocks private ClassicCreatePageRedirector redirector;

  @Test
  void should_redirect_to_create() {
    // When a request is processed to create
    ResponseEntity<Void> response = redirector.view();

    // Then classic is prepared
    verify(preparer).prepare();

    // And a redirect is issued to classic create
    String location = response.getHeaders().get("Location").get(0);
    assertEquals("/nbs/ManagePage.do?method=addPageLoad", location);
  }
}
