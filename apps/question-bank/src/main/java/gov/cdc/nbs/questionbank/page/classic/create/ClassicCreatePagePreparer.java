package gov.cdc.nbs.questionbank.page.classic.create;

import org.springframework.stereotype.Component;
import gov.cdc.nbs.questionbank.page.classic.redirect.outgoing.ClassicManagePagesRequester;

@Component
public class ClassicCreatePagePreparer {
  private final ClassicManagePagesRequester managePagesRequest;

  ClassicCreatePagePreparer(
      final ClassicManagePagesRequester managePagesRequest) {
    this.managePagesRequest = managePagesRequest;
  }

  public void prepare() {
    this.managePagesRequest.request();
  }
}
