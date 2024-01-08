package gov.cdc.nbs.questionbank.page.classic;

import org.springframework.stereotype.Component;
import gov.cdc.nbs.questionbank.page.classic.redirect.outgoing.ClassicManagePagesRequester;
import gov.cdc.nbs.questionbank.page.classic.redirect.outgoing.ClassicViewPageRequester;

@Component
public class ClassicEditPagePreparer {
  private final ClassicManagePagesRequester managePageRequest;
  private final ClassicViewPageRequester viewPageRequest;

  ClassicEditPagePreparer(
      final ClassicManagePagesRequester managePageRequest,
      final ClassicViewPageRequester viewPageRequest) {
    this.managePageRequest = managePageRequest;
    this.viewPageRequest = viewPageRequest;
  }

  public void prepare(long page) {
    this.managePageRequest.request();
    this.viewPageRequest.request(page);
  }
}
