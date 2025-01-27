package gov.cdc.nbs.patient.profile.identification.change;

import java.time.LocalDate;

public record NewPatientIdentificationInput(
    long patient,
    LocalDate asOf,
    String type,
    String authority,
    String value
) {
}
