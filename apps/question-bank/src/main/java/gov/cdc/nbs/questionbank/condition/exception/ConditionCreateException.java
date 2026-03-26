package gov.cdc.nbs.questionbank.condition.exception;

import gov.cdc.nbs.questionbank.exception.BadRequestException;

public class ConditionCreateException extends BadRequestException {
  public ConditionCreateException(String message) {
    super(message);
  }
}
