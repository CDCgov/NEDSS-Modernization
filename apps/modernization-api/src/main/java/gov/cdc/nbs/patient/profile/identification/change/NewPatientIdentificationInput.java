package gov.cdc.nbs.patient.profile.identification.change;

import java.time.Instant;

record NewPatientIdentificationInput(
    long patient,
    Instant asOf,
    String type,
    String authority,
    String value
) {
}
