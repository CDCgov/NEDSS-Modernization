package gov.cdc.nbs.web.response;

public sealed interface StandardResponse {

  record Success() implements StandardResponse {}

  record Failure(String reason) implements StandardResponse {
  }
}
