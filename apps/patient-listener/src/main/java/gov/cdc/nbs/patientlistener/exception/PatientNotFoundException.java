package gov.cdc.nbs.patientlistener.exception;

public class PatientNotFoundException extends KafkaException {

    public PatientNotFoundException(long patientId, String key) {
        super("Failed to find patient: " + patientId, key);
    }

}
