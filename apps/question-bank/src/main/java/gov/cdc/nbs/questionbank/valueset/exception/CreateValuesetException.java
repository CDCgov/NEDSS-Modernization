package gov.cdc.nbs.questionbank.valueset.exception;

import gov.cdc.nbs.questionbank.exception.BadRequestException;

public class CreateValuesetException extends BadRequestException {

  public CreateValuesetException(String message) {
    super(message);
  }
}
