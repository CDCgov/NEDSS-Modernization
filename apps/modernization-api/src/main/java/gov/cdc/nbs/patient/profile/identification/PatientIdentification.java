package gov.cdc.nbs.patient.profile.identification;

import java.time.LocalDate;

record PatientIdentification(
    long patient,
    long sequence,
    short version,
    LocalDate asOf,
    Type type,
    Authority authority,
    String value

) {
  record Type(
      String id,
      String description
  ) {
  }


  record Authority(
      String id,
      String description
  ) {
  }
}
