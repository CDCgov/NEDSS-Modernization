package gov.cdc.nbs.web.response;

public sealed interface StandardResponse {
  record Failure(String reason) implements StandardResponse {
  }
}
