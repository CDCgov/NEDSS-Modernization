package gov.cdc.nbs.patient.profile.administrative;

import java.time.Instant;

public record PatientAdministrative(long patient, long id, short version, Instant asOf, String comment) {
}
