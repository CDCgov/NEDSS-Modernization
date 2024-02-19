package gov.cdc.nbs.questionbank.valueset.exception;

import gov.cdc.nbs.questionbank.exception.InternalServerException;

public class UpdateConceptException extends InternalServerException {
  public UpdateConceptException(String message) {
    super(message);
  }
}
