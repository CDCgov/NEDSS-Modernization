package gov.cdc.nbs.patientlistener.request;

public class UserNotAuthorizedException extends PatientRequestException {

    public UserNotAuthorizedException(String key) {
        super("User not authorized to perform the specified operation", key);
    }

}
