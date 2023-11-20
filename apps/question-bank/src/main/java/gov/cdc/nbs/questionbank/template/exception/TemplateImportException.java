package gov.cdc.nbs.questionbank.template.exception;

import gov.cdc.nbs.questionbank.exception.BadRequestException;

public class TemplateImportException extends BadRequestException {
  public TemplateImportException(String message) {
    super(message);
  }
}
