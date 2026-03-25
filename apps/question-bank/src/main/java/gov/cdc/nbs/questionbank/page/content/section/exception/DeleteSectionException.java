package gov.cdc.nbs.questionbank.page.content.section.exception;

import gov.cdc.nbs.questionbank.exception.BadRequestException;

public class DeleteSectionException extends BadRequestException {

  public DeleteSectionException(String message) {
    super(message);
  }
}
