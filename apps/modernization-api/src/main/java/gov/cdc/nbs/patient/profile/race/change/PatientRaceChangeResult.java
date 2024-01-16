package gov.cdc.nbs.patient.profile.race.change;

public sealed interface PatientRaceChangeResult {

  long patient();

  record PatientRaceChangeSuccessful(long patient) implements PatientRaceChangeResult {
  }

  record PatientRaceChangeFailureExistingCategory(long patient, String category) implements PatientRaceChangeResult {
  }
}
