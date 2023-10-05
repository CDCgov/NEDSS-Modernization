package gov.cdc.nbs.address;

public record Address(
    String use,
    String address,
    String address2,
    String city,
    String state,
    String zipcode,

    String country
) {
}
