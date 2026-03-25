package gov.cdc.nbs.questionbank.page.content.subsection.exception;

import gov.cdc.nbs.questionbank.exception.BadRequestException;

public class UpdateSubSectionException extends BadRequestException {

  public UpdateSubSectionException(String message) {
    super(message);
  }
}
