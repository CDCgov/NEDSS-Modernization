package gov.cdc.nbs.questionbank.question.exception;

import gov.cdc.nbs.questionbank.exception.BadRequestException;

public class UpdateQuestionException extends BadRequestException {
  public UpdateQuestionException(String message) {
    super(message);
  }
}
