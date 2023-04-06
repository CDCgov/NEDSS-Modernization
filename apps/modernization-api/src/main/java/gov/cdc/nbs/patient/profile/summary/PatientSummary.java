package gov.cdc.nbs.patient.profile.summary;

import java.time.LocalDate;

public record PatientSummary(
    Name legalName,
    LocalDate birthday,
    Integer age,
    String gender,
    String ethnicity,
    String race
) {

    record Name(
        String prefix,
        String first,
        String middle,
        String last,
        String suffix
    ) {
    }
}
