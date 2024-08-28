package gov.cdc.nbs.patient.profile;

import java.time.Instant;
import java.util.List;

public record NewPatient(
    Instant asOf,
    String comment,
    List<Name> names,
    List<Address> addresses) {
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
