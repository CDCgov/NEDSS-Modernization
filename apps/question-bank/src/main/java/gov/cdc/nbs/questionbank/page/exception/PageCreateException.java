package gov.cdc.nbs.questionbank.page.exception;

import gov.cdc.nbs.questionbank.exception.BadRequestException;

public class PageCreateException extends BadRequestException {
  public PageCreateException(String message) {
    super(message);
  }
}
