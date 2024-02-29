package gov.cdc.nbs.questionbank.page.content.question.exception;

import gov.cdc.nbs.questionbank.exception.BadRequestException;

public class FindEditableQuestionException extends BadRequestException {
  public FindEditableQuestionException(String message) {
    super(message);
  }
}
