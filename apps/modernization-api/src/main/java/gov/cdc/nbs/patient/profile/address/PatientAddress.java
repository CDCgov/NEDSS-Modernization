package gov.cdc.nbs.patient.profile.address;

import java.time.Instant;

record PatientAddress(
    long patient,
    long id,
    short version,
    Instant asOf,
    Type type,
    Use use,
    String address1,
    String address2,
    String city,
    County county,
    State state,
    String zipcode,
    Country country,
    String censusTract,
    String comment
) {

    record Type(
        String id,
        String description
    ) {}

    record Use(
        String id,
        String description
    ) {}

    record County(
        String id,
        String description
    ) {}

    record State(
        String id,
        String description
    ) {}

    record Country(
        String id,
        String description
    ) {}
}
