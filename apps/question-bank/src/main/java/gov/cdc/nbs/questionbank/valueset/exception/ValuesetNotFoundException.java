package gov.cdc.nbs.questionbank.valueset.exception;

import gov.cdc.nbs.questionbank.exception.NotFoundException;

public class ValuesetNotFoundException extends NotFoundException {
  public ValuesetNotFoundException(String valueset) {
    super("Failed to find Value Set: " + valueset);
  }
}
