package gov.cdc.nbs.questionbank.valueset.exception;

import gov.cdc.nbs.questionbank.exception.BadRequestException;

public class DuplicateConceptException extends BadRequestException {
  public DuplicateConceptException(String codesetName, String code) {
    super("ValueSet %s already contains a concept with code: %s".formatted(codesetName, code));
  }
}
