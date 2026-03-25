package gov.cdc.nbs.questionbank.page.content.question.exception;

import gov.cdc.nbs.questionbank.exception.BadRequestException;

public class UpdatePageQuestionException extends BadRequestException {
  public UpdatePageQuestionException(String message) {
    super(message);
  }
}
