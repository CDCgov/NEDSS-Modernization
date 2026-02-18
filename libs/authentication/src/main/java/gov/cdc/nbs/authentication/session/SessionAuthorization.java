package gov.cdc.nbs.authentication.session;

public sealed interface SessionAuthorization {

  record Authorized(String user) implements SessionAuthorization {}

  record Unauthorized() implements SessionAuthorization {}
}
