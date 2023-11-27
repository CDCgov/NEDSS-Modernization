package gov.cdc.nbs.questionbank.page.classic;

import org.springframework.stereotype.Component;
import gov.cdc.nbs.questionbank.page.classic.redirect.outgoing.ClassicManagePageRequester;

@Component
public class ClassicPreviewPagePreparer {
  private final ClassicManagePageRequester managePageRequest;

  ClassicPreviewPagePreparer(
      final ClassicManagePageRequester managePageRequest) {
    this.managePageRequest = managePageRequest;
  }

  public void prepare() {
    this.managePageRequest.request();
  }
}
