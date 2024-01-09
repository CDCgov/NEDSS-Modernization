package gov.cdc.nbs.questionbank.page.summary.download.exceptions;

import gov.cdc.nbs.questionbank.exception.InternalServerException;

public class CsvCreationException extends InternalServerException {
  public CsvCreationException(String message) {
    super(message);
  }
}
