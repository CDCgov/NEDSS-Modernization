package gov.cdc.nbs.questionbank.page.exception;

import gov.cdc.nbs.questionbank.exception.BadRequestException;

public class PageUpdateException extends BadRequestException {
  public PageUpdateException(String message) {
    super(message);
  }
}
