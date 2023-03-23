package gov.cdc.nbs.patientlistener.request;

public class PatientRequestException extends RuntimeException {
    private final String key;

    public PatientRequestException(String message, String key) {
        super(message);
        this.key = key;
    }

    public String getKey() {
        return key;
    }

}
