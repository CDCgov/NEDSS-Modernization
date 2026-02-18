package gov.cdc.nbs.questionbank.page.content.subsection.exception;

import gov.cdc.nbs.questionbank.exception.BadRequestException;

public class CreateSubsectionException extends BadRequestException {

  public CreateSubsectionException(String message) {
    super(message);
  }
}
