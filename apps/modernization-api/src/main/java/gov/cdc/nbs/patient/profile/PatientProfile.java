package gov.cdc.nbs.patient.profile;

public record PatientProfile(long id, String local, short version, String recordStatusCd) {
}
