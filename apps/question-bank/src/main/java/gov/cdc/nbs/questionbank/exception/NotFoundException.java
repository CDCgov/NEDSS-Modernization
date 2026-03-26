package gov.cdc.nbs.questionbank.exception;

public abstract class NotFoundException extends RuntimeException {
  protected NotFoundException(String message) {
    super(message);
  }
}
