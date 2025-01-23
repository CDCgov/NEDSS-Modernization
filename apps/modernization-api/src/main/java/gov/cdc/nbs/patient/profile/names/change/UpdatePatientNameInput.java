package gov.cdc.nbs.patient.profile.names.change;

import java.time.LocalDate;

public record UpdatePatientNameInput(
    long patient,
    int sequence,
    LocalDate asOf,
    String type,
    String prefix,
    String first,
    String middle,
    String secondMiddle,
    String last,
    String secondLast,
    String suffix,
    String degree
) {
}
