package gov.cdc.nbs.questionbank.valueset.exception;

import gov.cdc.nbs.questionbank.exception.BadRequestException;

public class ValuesetUpdateException extends BadRequestException {
  public ValuesetUpdateException(String message) {
    super(message);
  }
}
