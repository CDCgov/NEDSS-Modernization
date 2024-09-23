package gov.cdc.nbs.patient.profile.address;

import java.time.Instant;

public record AddressDemographic(
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
