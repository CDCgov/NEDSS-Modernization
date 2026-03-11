package gov.cdc.nbs.questionbank.question.exception;

import gov.cdc.nbs.questionbank.exception.BadRequestException;

public class CreateQuestionException extends BadRequestException {
  public CreateQuestionException(String message) {
    super(message);
  }
}
