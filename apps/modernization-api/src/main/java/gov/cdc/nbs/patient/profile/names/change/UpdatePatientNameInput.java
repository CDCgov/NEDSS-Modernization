package gov.cdc.nbs.patient.profile.names.change;

import java.time.Instant;

public record UpdatePatientNameInput(
    long patient,
    int sequence,
    Instant asOf,
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
