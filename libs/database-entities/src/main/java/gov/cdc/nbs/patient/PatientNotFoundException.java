package gov.cdc.nbs.patient;

public class PatientNotFoundException extends PatientException {
  public PatientNotFoundException(final long identifier) {
    super(identifier, String.format("Unable to find patient: %d", identifier));
  }
}
