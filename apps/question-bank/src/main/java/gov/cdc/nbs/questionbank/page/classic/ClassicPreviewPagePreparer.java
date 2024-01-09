package gov.cdc.nbs.questionbank.page.classic;

import org.springframework.stereotype.Component;
import gov.cdc.nbs.questionbank.page.classic.redirect.outgoing.ClassicManagePagesRequester;

@Component
public class ClassicPreviewPagePreparer {
  private final ClassicManagePagesRequester managePageRequest;

  ClassicPreviewPagePreparer(
      final ClassicManagePagesRequester managePageRequest) {
    this.managePageRequest = managePageRequest;
  }

  public void prepare() {
    this.managePageRequest.request();
  }
}
