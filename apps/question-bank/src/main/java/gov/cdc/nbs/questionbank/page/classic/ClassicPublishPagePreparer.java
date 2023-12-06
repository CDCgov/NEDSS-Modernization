package gov.cdc.nbs.questionbank.page.classic;

import org.springframework.stereotype.Component;
import gov.cdc.nbs.questionbank.page.classic.redirect.outgoing.ClassicManagePageRequester;
import gov.cdc.nbs.questionbank.page.classic.redirect.outgoing.ClassicPreviewPageRequester;

@Component
public class ClassicPublishPagePreparer {
    private final ClassicManagePageRequester managePageRequest;
    private final ClassicPreviewPageRequester previewPageRequest;

  ClassicPublishPagePreparer(
      final ClassicManagePageRequester managePageRequest,
      final ClassicPreviewPageRequester previewPageRequester) {
    this.managePageRequest = managePageRequest;
    this.previewPageRequest = previewPageRequester;
  }

  public void prepare(final long page) {
    this.managePageRequest.request();

    this.previewPageRequest.request(page);
  }
}
