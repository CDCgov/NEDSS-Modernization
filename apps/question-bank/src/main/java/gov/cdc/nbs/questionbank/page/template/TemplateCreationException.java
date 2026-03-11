package gov.cdc.nbs.questionbank.page.template;

import gov.cdc.nbs.questionbank.exception.BadRequestException;

public class TemplateCreationException extends BadRequestException {
  public TemplateCreationException(String message) {
    super(message);
  }
}
