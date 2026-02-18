package gov.cdc.nbs.questionbank.page.content.section.exception;

import gov.cdc.nbs.questionbank.exception.BadRequestException;

public class CreateSectionException extends BadRequestException {

  public CreateSectionException(String message) {
    super(message);
  }
}
