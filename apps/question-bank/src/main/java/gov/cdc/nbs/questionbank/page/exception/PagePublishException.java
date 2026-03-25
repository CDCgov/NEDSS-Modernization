package gov.cdc.nbs.questionbank.page.exception;

import gov.cdc.nbs.questionbank.exception.BadRequestException;

public class PagePublishException extends BadRequestException {
  public PagePublishException(String message) {
    super(message);
  }
}
