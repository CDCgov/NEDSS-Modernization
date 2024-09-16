package gov.cdc.nbs.patient.profile.create;

import java.time.Instant;
import java.util.Collections;
import java.util.List;

public record NewPatient(
    Administrative administrative,
    List<Name> names,
    List<Address> addresses,
    List<Phone> phoneEmails,
    List<Race> races,
    List<Identification> identifications
) {


  public record Administrative(
      Instant asOf,
      String comment
  ) {

    public Administrative(Instant asOf) {
      this(asOf, null);
    }

    public Administrative withComment(final String comment) {
      return new Administrative(asOf(), comment);
    }
  }

  public record Name(
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


  public record Address(
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


  public record Phone(
      Instant asOf,
      String type,
      String use,
      String countryCode,
      String number,
      String extension,
      String email,
      String url,
      String comment
  ) {
  }


  public record Race(
      Instant asOf,
      String race,
      List<String> detailed
  ) {
  }


  public record Identification(
      Instant asOf,
      String type,
      String issuer,
      String id
  ) {
  }

  public NewPatient(Instant asOf) {
    this(
        new Administrative(asOf),
        Collections.emptyList(),
        Collections.emptyList(),
        Collections.emptyList(),
        Collections.emptyList(),
        Collections.emptyList()
    );
  }

}
