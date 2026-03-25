package gov.cdc.nbs.questionbank.page.content.subsection.exception;

import gov.cdc.nbs.questionbank.exception.BadRequestException;

public class ValidateSubsectionException extends BadRequestException {

  public ValidateSubsectionException(String message) {
    super(message);
  }
}
