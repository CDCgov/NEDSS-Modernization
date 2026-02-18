package gov.cdc.nbs.questionbank.page.content.staticelement.exceptions;

import gov.cdc.nbs.questionbank.exception.BadRequestException;

public class DeleteStaticElementException extends BadRequestException {
  public DeleteStaticElementException(String message) {
    super(message);
  }
}
