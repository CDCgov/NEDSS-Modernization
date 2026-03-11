package gov.cdc.nbs.demographics.address;

public record DisplayableAddress(
    String use, String address, String address2, String city, String state, String zipcode) {}
