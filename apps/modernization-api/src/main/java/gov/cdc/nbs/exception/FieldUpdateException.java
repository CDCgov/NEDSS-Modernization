package gov.cdc.nbs.exception;

public class FieldUpdateException extends RuntimeException {

  public FieldUpdateException() {
    super("The update operation failed due to a bad field.");
  }
}
