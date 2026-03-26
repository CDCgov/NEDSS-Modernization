package gov.cdc.nbs.questionbank.page.clone;

import gov.cdc.nbs.questionbank.page.classic.redirect.outgoing.ClassicManagePagesRequester;
import gov.cdc.nbs.questionbank.page.classic.redirect.outgoing.ClassicPageDetailsRequester;
import gov.cdc.nbs.questionbank.page.classic.redirect.outgoing.ClassicPreviewPageRequester;
import org.springframework.stereotype.Component;

@Component
class ClassicClonePagePreparer {

  private final ClassicManagePagesRequester managePageRequester;
  private final ClassicPreviewPageRequester previewPageRequester;
  private final ClassicPageDetailsRequester pageDetailsRequester;

  ClassicClonePagePreparer(
      final ClassicManagePagesRequester managePageRequester,
      final ClassicPreviewPageRequester previewPageRequester,
      final ClassicPageDetailsRequester pageDetailsRequester) {
    this.managePageRequester = managePageRequester;
    this.previewPageRequester = previewPageRequester;
    this.pageDetailsRequester = pageDetailsRequester;
  }

  void prepare(final long page) {
    //  simulates navigating to Manage Pages
    this.managePageRequester.request();
    //  simulates previewing a page
    this.previewPageRequester.request(page);
    //  simulates navigating to Page Details
    this.pageDetailsRequester.request();
  }
}
