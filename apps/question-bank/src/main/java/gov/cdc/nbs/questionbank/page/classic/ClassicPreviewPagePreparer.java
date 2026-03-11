package gov.cdc.nbs.questionbank.page.classic;

import gov.cdc.nbs.questionbank.page.classic.redirect.outgoing.ClassicManagePagesRequester;
import org.springframework.stereotype.Component;

@Component
public class ClassicPreviewPagePreparer {
  private final ClassicManagePagesRequester managePageRequest;

  ClassicPreviewPagePreparer(final ClassicManagePagesRequester managePageRequest) {
    this.managePageRequest = managePageRequest;
  }

  public void prepare() {
    this.managePageRequest.request();
  }
}
