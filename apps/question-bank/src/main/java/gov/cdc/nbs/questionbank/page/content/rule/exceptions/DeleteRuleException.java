package gov.cdc.nbs.questionbank.page.content.rule.exceptions;

import gov.cdc.nbs.questionbank.exception.BadRequestException;

public class DeleteRuleException extends BadRequestException {

  public DeleteRuleException(String message) {
    super(message);
  }
}
