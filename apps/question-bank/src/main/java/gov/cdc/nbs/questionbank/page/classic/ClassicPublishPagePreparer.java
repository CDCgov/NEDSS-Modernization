package gov.cdc.nbs.questionbank.page.classic;

import org.springframework.stereotype.Component;
import gov.cdc.nbs.questionbank.page.classic.redirect.outgoing.ClassicManagePageRequester;
import gov.cdc.nbs.questionbank.page.classic.redirect.outgoing.ClassicPreviewPageRequester;
import gov.cdc.nbs.questionbank.page.classic.redirect.outgoing.ClassicPublishPageLoadRequest;

@Component
public class ClassicPublishPagePreparer {
  private final ClassicManagePageRequester managePageRequest;
  private final ClassicPreviewPageRequester previewPageRequest;
  private final ClassicPublishPageLoadRequest publishPageLoadRequest;

  ClassicPublishPagePreparer(
      final ClassicManagePageRequester managePageRequest,
      final ClassicPreviewPageRequester previewPageRequester,
      final ClassicPublishPageLoadRequest publishPageLoadRequest) {
    this.managePageRequest = managePageRequest;
    this.previewPageRequest = previewPageRequester;
    this.publishPageLoadRequest = publishPageLoadRequest;
  }

  public void prepare(final long page) {
    this.managePageRequest.request();

    this.previewPageRequest.request(page);

    this.publishPageLoadRequest.request();
  }
}
