package gov.cdc.nbs.questionbank.page.content.section.exception;

import gov.cdc.nbs.questionbank.exception.BadRequestException;

public class UpdateSectionException extends BadRequestException {

  public UpdateSectionException(String message) {
    super(message);
  }
}
