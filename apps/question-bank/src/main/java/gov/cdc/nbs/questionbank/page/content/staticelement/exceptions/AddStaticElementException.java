package gov.cdc.nbs.questionbank.page.content.staticelement.exceptions;

import gov.cdc.nbs.questionbank.exception.BadRequestException;

public class AddStaticElementException extends BadRequestException {

  public AddStaticElementException(String message) {
    super(message);
  }
}
