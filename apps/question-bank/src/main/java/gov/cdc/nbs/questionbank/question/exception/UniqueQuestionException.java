package gov.cdc.nbs.questionbank.question.exception;

import gov.cdc.nbs.questionbank.exception.BadRequestException;

public class UniqueQuestionException extends BadRequestException {
  public UniqueQuestionException(String message) {
    super(message);
  }
}
