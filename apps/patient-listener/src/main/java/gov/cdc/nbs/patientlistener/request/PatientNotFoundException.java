package gov.cdc.nbs.patientlistener.request;

public class PatientNotFoundException extends PatientRequestException {

    public PatientNotFoundException(long patientId, String key) {
        super("Failed to find patient: " + patientId, key);
    }

}
