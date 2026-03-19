package gov.cdc.nbs.questionbank.question.exception;

import gov.cdc.nbs.questionbank.exception.NotFoundException;

public class QuestionNotFoundException extends NotFoundException {
  public QuestionNotFoundException(String message) {
    super(message);
  }

  public QuestionNotFoundException(Long id) {
    super("Unable to find question with id: " + id);
  }
}
