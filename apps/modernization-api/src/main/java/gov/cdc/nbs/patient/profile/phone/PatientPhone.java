package gov.cdc.nbs.patient.profile.phone;

import java.time.Instant;

record PatientPhone(
    long patient,
    long id,
    short version,
    Instant asOf,
    Type type,
    Use use,
    String countryCode,
    String number,
    String extension,
    String email,
    String url,
    String comment
) {
    record Type(
        String id,
        String description
    ) {
    }


    record Use(
        String id,
        String description
    ) {
    }
}
