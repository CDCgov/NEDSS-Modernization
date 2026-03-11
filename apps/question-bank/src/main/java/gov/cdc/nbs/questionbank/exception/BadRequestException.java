package gov.cdc.nbs.questionbank.exception;

public abstract class BadRequestException extends RuntimeException {
  protected BadRequestException(String message) {
    super(message);
  }
}
