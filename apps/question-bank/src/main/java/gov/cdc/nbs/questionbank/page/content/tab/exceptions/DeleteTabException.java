package gov.cdc.nbs.questionbank.page.content.tab.exceptions;

import gov.cdc.nbs.questionbank.exception.BadRequestException;

public class DeleteTabException extends BadRequestException {

  public DeleteTabException(String message) {
    super(message);
  }
}
