package gov.cdc.nbs.patient.profile.administrative;

import java.time.Instant;

public record NewPatientAdministrative(Instant asOf, String comment) {
}
