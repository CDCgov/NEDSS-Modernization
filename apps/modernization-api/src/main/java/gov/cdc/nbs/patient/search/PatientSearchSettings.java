package gov.cdc.nbs.patient.search;

record PatientSearchSettings(NameBoost first, NameBoost last) {

  record NameBoost(float primary, float nonPrimary, float soundex) {}
}
