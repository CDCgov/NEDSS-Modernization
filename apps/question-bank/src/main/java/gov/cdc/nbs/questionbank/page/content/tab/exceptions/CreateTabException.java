package gov.cdc.nbs.questionbank.page.content.tab.exceptions;

import gov.cdc.nbs.questionbank.exception.BadRequestException;

public class CreateTabException extends BadRequestException {

  public CreateTabException(String message) {
    super(message);
  }
}
