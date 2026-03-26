package gov.cdc.nbs.questionbank.page.exception;

import gov.cdc.nbs.questionbank.exception.BadRequestException;

public class DeleteQuestionException extends BadRequestException {
  public DeleteQuestionException(String message) {
    super(message);
  }
}
