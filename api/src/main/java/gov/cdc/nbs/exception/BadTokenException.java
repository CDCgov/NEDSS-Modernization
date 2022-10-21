package gov.cdc.nbs.exception;

public class BadTokenException extends RuntimeException {
    public BadTokenException() {
        super("Invalid token supplied");
    }
}
