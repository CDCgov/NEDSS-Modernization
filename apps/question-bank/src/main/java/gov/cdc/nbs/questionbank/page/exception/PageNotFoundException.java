package gov.cdc.nbs.questionbank.page.exception;

import gov.cdc.nbs.questionbank.exception.NotFoundException;

public class PageNotFoundException extends NotFoundException {

  public PageNotFoundException() {
    super("Could not find page with given id.");
  }

  public PageNotFoundException(final long identifier) {
    super("A Page identified by %d cannot be found".formatted(identifier));
  }
}
