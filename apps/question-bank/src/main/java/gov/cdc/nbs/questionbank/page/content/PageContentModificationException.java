package gov.cdc.nbs.questionbank.page.content;

import gov.cdc.nbs.questionbank.exception.BadRequestException;

public class PageContentModificationException extends BadRequestException {
  public PageContentModificationException(String message) {
    super(message);
  }
}
