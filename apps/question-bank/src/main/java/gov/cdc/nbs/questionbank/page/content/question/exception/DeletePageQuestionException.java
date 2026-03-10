package gov.cdc.nbs.questionbank.page.content.question.exception;

import gov.cdc.nbs.questionbank.exception.BadRequestException;

public class DeletePageQuestionException extends BadRequestException {
  public DeletePageQuestionException(String message) {
    super(message);
  }
}
