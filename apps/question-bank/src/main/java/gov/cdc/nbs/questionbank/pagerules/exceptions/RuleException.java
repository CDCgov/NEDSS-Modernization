package gov.cdc.nbs.questionbank.pagerules.exceptions;

import gov.cdc.nbs.questionbank.exception.BadRequestException;

public class RuleException extends BadRequestException {
  public RuleException(String message) {
    super(message);
  }
}
