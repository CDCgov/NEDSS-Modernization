package gov.cdc.nbs.questionbank.exception;

public class QueryException extends BadRequestException {
  public QueryException(String message) {
    super(message);
  }
}
