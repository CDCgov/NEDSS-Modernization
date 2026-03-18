package gov.cdc.nbs.questionbank.condition.exception;

import gov.cdc.nbs.questionbank.exception.InternalServerException;

public class ConditionInternalServerError extends InternalServerException {
  public ConditionInternalServerError(String id) {
    super("There was an internal server error associated with id: %s".formatted(id));
  }
}
