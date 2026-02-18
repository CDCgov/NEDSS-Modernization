package gov.cdc.nbs.questionbank.page.classic;

import gov.cdc.nbs.questionbank.page.classic.redirect.outgoing.ClassicManagePagesRequester;
import gov.cdc.nbs.questionbank.page.classic.redirect.outgoing.ClassicPreviewPageRequester;
import gov.cdc.nbs.questionbank.page.classic.redirect.outgoing.ClassicPublishPageLoadRequest;
import org.springframework.stereotype.Component;

@Component
public class ClassicPublishPagePreparer {
  private final ClassicManagePagesRequester managePageRequest;
  private final ClassicPreviewPageRequester previewPageRequest;
  private final ClassicPublishPageLoadRequest publishPageLoadRequest;

  ClassicPublishPagePreparer(
      final ClassicManagePagesRequester managePageRequest,
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
