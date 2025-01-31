package gov.cdc.nbs.patient.profile.names;

import java.time.LocalDate;

public record PatientName(
    long patient,
    short version,
    int sequence,
    LocalDate asOf,
    Use use,
    Prefix prefix,
    String first,
    String middle,
    String secondMiddle,
    String last,
    String secondLast,
    Suffix suffix,
    Degree degree
) {

  record Use(
      String id,
      String description
  ) {
  }


  record Prefix(
      String id,
      String description
  ) {
  }


  record Suffix(
      String id,
      String description
  ) {
  }


  record Degree(
      String id,
      String description
  ) {
  }
}
