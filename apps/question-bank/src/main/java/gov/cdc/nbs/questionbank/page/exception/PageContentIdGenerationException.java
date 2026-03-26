package gov.cdc.nbs.questionbank.page.exception;

import gov.cdc.nbs.questionbank.exception.InternalServerException;

public class PageContentIdGenerationException extends InternalServerException {
  public PageContentIdGenerationException() {
    super("Failed to generate an Id!");
  }
}
