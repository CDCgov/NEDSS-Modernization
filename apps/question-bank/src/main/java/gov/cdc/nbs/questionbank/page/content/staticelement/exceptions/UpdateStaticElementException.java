package gov.cdc.nbs.questionbank.page.content.staticelement.exceptions;

import gov.cdc.nbs.questionbank.exception.BadRequestException;

public class UpdateStaticElementException extends BadRequestException {
  public UpdateStaticElementException(String message) {
    super(message);
  }
}
