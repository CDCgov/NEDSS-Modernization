package gov.cdc.nbs.patient.profile;

import java.time.Instant;
import java.util.List;

public record NewPatient(
    Administrative administrative,
    List<Name> names,
    List<Address> addresses,
    List<Phone> phoneEmails,
    List<Race> races,
    List<Identification> identifications) {
}


record Administrative(
    Instant asOf,
    String comment) {
}


record Name(
    Instant asOf,
    String type,
    String prefix,
    String first,
    String middle,
    String secondMiddle,
    String last,
    String secondLast,
    String suffix,
    String degree) {
}


record Address(
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
    String comment) {
}


record Phone(
    Instant asOf,
    String type,
    String use,
    String countryCode,
    String phoneNumber,
    String extension,
    String email,
    String url,
    String comment) {
}


record Race(
    Instant asOf,
    String race,
    List<String> detailed) {
}


record Identification(
    Instant asOf,
    String type,
    String authority,
    String value) {
}
