package gov.cdc.nbs.questionbank.exception;

public class NullObjectException extends BadRequestException {

  public NullObjectException(String message) {
    super(message);
  }
}
