package gov.cdc.nbs.questionbank.page.content.tab.exceptions;

import gov.cdc.nbs.questionbank.exception.BadRequestException;

public class UpdateTabException extends BadRequestException {

  public UpdateTabException(String message) {
    super(message);
  }
}
