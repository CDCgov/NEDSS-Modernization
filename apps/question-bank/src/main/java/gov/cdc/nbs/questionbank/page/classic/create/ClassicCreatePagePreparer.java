package gov.cdc.nbs.questionbank.page.classic.create;

import gov.cdc.nbs.questionbank.page.classic.redirect.outgoing.ClassicManagePagesRequester;
import org.springframework.stereotype.Component;

@Component
public class ClassicCreatePagePreparer {
  private final ClassicManagePagesRequester managePagesRequest;

  ClassicCreatePagePreparer(final ClassicManagePagesRequester managePagesRequest) {
    this.managePagesRequest = managePagesRequest;
  }

  public void prepare() {
    this.managePagesRequest.request();
  }
}
