package gov.cdc.nbs.patientlistener.exception;

public class UserNotAuthorizedException extends KafkaException {

    public UserNotAuthorizedException(String key) {
        super("User not authorized to perform the specified operation", key);
    }

}
