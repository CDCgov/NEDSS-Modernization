package gov.cdc.nbs.patient;

public class PatientHasAssociatedEventsException extends PatientException {

  public PatientHasAssociatedEventsException(final long patient) {
    super(patient, "Cannot delete patient with Active Revisions");
  }
}
