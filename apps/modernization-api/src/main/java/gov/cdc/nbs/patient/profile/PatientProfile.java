package gov.cdc.nbs.patient.profile;

import java.time.Instant;

public record PatientProfile(long id, String local, Instant asOf, short version) {
}
