package gov.cdc.nbs.questionbank.exception;

public abstract class InternalServerException extends RuntimeException {

  protected InternalServerException(String message) {
    super(message);
  }
}
