package gov.cdc.nbs.questionbank.page.exception;

import gov.cdc.nbs.questionbank.exception.NotFoundException;

public class PageNotFoundException extends NotFoundException {

  public PageNotFoundException(final long identifier) {
    super(String.format("A Page identified by %d cannot be found", identifier));
  }

}
