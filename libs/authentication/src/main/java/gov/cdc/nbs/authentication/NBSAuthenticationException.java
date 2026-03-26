package gov.cdc.nbs.authentication;

public class NBSAuthenticationException extends RuntimeException {

  public NBSAuthenticationException() {
    super("There is no active NBS session.");
  }
}
