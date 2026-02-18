package gov.cdc.nbs.questionbank.template.exception;

import gov.cdc.nbs.questionbank.exception.NotFoundException;

public class TemplateNotFoundException extends NotFoundException {
  public TemplateNotFoundException(Long id) {
    super("Failed to find Template with id: " + id);
  }
}
