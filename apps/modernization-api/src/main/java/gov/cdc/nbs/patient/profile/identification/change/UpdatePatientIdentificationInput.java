package gov.cdc.nbs.patient.profile.identification.change;

import java.time.Instant;

record UpdatePatientIdentificationInput(
    long patient,
    int sequence,
    Instant asOf,
    String type,
    String authority,
    String value
) {
}
