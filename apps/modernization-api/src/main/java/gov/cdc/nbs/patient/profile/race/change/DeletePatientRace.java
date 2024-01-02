package gov.cdc.nbs.patient.profile.race.change;

record DeletePatientRace(long patient, String category) {

  DeletePatientRace withPatient(final long patient) {
    return new DeletePatientRace(patient, category());
  }

  DeletePatientRace withCategory(final String category) {
    return new DeletePatientRace(patient(), category);
  }
}
