package gov.cdc.nbs.questionbank.kafka.exception;

public class UserNotAuthorizedException extends RequestException {

    public UserNotAuthorizedException(String key) {
        super("User not authorized to perform the requested action", key);
    }

}
