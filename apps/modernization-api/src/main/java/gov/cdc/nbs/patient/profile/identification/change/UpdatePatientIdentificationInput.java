package gov.cdc.nbs.patient.profile.identification.change;

import java.time.LocalDate;

record UpdatePatientIdentificationInput(
    long patient,
    int sequence,
    LocalDate asOf,
    String type,
    String authority,
    String value
) {
}
