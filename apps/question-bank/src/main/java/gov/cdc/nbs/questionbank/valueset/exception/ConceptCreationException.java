package gov.cdc.nbs.questionbank.valueset.exception;

import gov.cdc.nbs.questionbank.exception.InternalServerException;

public class ConceptCreationException extends InternalServerException {
  public ConceptCreationException(String message) {
    super(message);
  }
}
