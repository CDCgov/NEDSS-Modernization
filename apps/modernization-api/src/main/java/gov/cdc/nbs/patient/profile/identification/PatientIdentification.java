package gov.cdc.nbs.patient.profile.identification;

import java.time.Instant;

record PatientIdentification(
    long patient,
    long sequence,
    short version,
    Instant asOf,
    Type type,
    Authority authority,
    String value

) {
    record Type(
        String id,
        String description
    ) {
    }


    record Authority(
        String id,
        String description
    ) {
    }
}
