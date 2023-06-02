package gov.cdc.nbs.patient;

public class PatientException extends Exception {

    private final long patient;

    public PatientException(final long patient, final String message) {
        super(message);
        this.patient = patient;
    }

    public long patient() {
        return patient;
    }
}
