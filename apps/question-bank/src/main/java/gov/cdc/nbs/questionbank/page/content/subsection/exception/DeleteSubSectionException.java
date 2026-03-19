package gov.cdc.nbs.questionbank.page.content.subsection.exception;

import gov.cdc.nbs.questionbank.exception.BadRequestException;

public class DeleteSubSectionException extends BadRequestException {

  public DeleteSubSectionException(String message) {
    super(message);
  }
}
