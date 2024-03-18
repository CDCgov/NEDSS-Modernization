package gov.cdc.nbs.questionbank.pagerules.exceptions;

import gov.cdc.nbs.questionbank.exception.NotFoundException;

public class RuleNotFoundException extends NotFoundException {
  public RuleNotFoundException(long id) {
    super("Failed to find rule with id: " + id);
  }
}
