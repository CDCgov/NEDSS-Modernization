package gov.cdc.nbs.patient.demographic.race;

import gov.cdc.nbs.patient.PatientException;

public class ExistingPatientRaceException extends PatientException {

  private final String category;

  public ExistingPatientRaceException(final long patient, final String category) {
    super(
        patient,
        String.format("A race demographic for %s already exists for the Patient.", category));
    this.category = category;
  }

  public String category() {
    return category;
  }
}
