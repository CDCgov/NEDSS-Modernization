package gov.cdc.nbs.questionbank.page.summary.download.exceptions;

import gov.cdc.nbs.questionbank.exception.InternalServerException;

public class PdfCreationException extends InternalServerException {
  public PdfCreationException(String message) {
    super(message);
  }
}
