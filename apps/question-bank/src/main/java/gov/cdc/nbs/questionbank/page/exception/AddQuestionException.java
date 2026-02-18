package gov.cdc.nbs.questionbank.page.exception;

import gov.cdc.nbs.questionbank.exception.BadRequestException;

public class AddQuestionException extends BadRequestException {
  public AddQuestionException(String message) {
    super(message);
  }
}
