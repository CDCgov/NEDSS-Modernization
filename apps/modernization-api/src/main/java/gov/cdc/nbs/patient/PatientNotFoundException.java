package gov.cdc.nbs.patient;

public class PatientNotFoundException extends PatientException{
    public PatientNotFoundException(final long patient) {
        super(patient, "Unable to find patient: " + patient);
    }
}
