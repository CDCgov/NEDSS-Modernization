package gov.cdc.nbs.questionbank.valueset.exception;

import gov.cdc.nbs.questionbank.exception.NotFoundException;

public class ConceptNotFoundException extends NotFoundException {
  public ConceptNotFoundException(String codeSetNm, String code) {
    super("Failed to find concept for Valueset: %s with code: %s".formatted(codeSetNm, code));
  }

  public ConceptNotFoundException(String message) {
    super(message);
  }
}
