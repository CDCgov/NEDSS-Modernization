package gov.cdc.nbs.questionbank.page.classic;

import gov.cdc.nbs.questionbank.page.classic.redirect.outgoing.ClassicManagePagesRequester;
import gov.cdc.nbs.questionbank.page.classic.redirect.outgoing.ClassicPreviewPageRequester;
import org.springframework.stereotype.Component;

@Component
public class ClassicCreateTemplatePreparer {

  private final ClassicManagePagesRequester managePagePreparer;
  private final ClassicPreviewPageRequester previewPagePreparer;
  private final ClassicSaveAsTemplateLoadRequester templateLoadPreparer;

  ClassicCreateTemplatePreparer(
      final ClassicManagePagesRequester managePagePreparer,
      final ClassicPreviewPageRequester previewPagePreparer,
      final ClassicSaveAsTemplateLoadRequester templateLoadPreparer) {
    this.managePagePreparer = managePagePreparer;
    this.previewPagePreparer = previewPagePreparer;
    this.templateLoadPreparer = templateLoadPreparer;
  }

  public void prepare(final long page) {
    //  simulates navigating to Manage Pages
    this.managePagePreparer.request();
    //  simulates previewing a page for edit
    this.previewPagePreparer.request(page);
    //  simulates requesting to save a page as a template (pop-up)
    this.templateLoadPreparer.request();
  }
}
