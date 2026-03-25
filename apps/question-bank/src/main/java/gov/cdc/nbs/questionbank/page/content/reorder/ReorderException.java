package gov.cdc.nbs.questionbank.page.content.reorder;

import gov.cdc.nbs.questionbank.exception.BadRequestException;

public class ReorderException extends BadRequestException {
  public ReorderException(String message) {
    super(message);
  }
}
