package gov.cdc.nbs.patient.profile.administrative;

import java.time.LocalDate;

public record PatientAdministrative(long patient, long id, short version, LocalDate asOf, String comment) {
}
