package gov.cdc.nbs.patient.profile.address.change;

import java.time.Instant;

public record UpdatePatientAddressInput(
    long patient,
    long id,
    Instant asOf,
    String type,
    String use,
    String address1,
    String address2,
    String city,
    String state,
    String zipcode,
    String county,
    String censusTract,
    String country,
    String comment
) {
}
