package gov.cdc.nbs.questionbank.page.template;

import org.springframework.stereotype.Component;

@Component
class ClassicViewPagePreparer {

  private final ClassicManagePagePreparer managePagePreparer;
  private final ClassicPreviewPagePreparer previewPagePreparer;
  private final ClassicSaveAsTemplateLoadPreparer templateLoadPreparer;

  ClassicViewPagePreparer(
      final ClassicManagePagePreparer managePagePreparer,
      final ClassicPreviewPagePreparer previewPagePreparer,
      final ClassicSaveAsTemplateLoadPreparer templateLoadPreparer
  ) {
    this.managePagePreparer = managePagePreparer;
    this.previewPagePreparer = previewPagePreparer;
    this.templateLoadPreparer = templateLoadPreparer;
  }

  void prepare(final long page) {
    //  simulates navigating to Manage Pages
    this.managePagePreparer.prepare();
    //  simulates previewing a page for edit
    this.previewPagePreparer.prepare(page);
    //  simulates requesting to save a page as a template (pop-up)
    this.templateLoadPreparer.prepare();
  }
}
