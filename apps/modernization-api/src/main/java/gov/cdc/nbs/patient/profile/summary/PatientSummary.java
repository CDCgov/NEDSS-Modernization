package gov.cdc.nbs.patient.profile.summary;

import java.time.LocalDate;
import java.util.Collection;

public record PatientSummary(
    LocalDate asOf,
    long patient,
    Name legalName,
    LocalDate birthday,
    Integer age,
    String gender,
    String ethnicity,
    Collection<Phone> phone,
    Collection<Email> email) {

  record Name(
      String prefix,
      String first,
      String middle,
      String last,
      String suffix) {
  }


  record Phone(
      String use,
      String number) {
  }


  record Email(
      String use,
      String address) {
  }


  record PatientSummaryIdentification(
      String type,
      String value) {
  }
}
