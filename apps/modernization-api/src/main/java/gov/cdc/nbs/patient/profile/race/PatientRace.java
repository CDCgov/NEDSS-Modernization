package gov.cdc.nbs.patient.profile.race;

import java.time.LocalDate;
import java.util.Collection;

record PatientRace(
    long patient,
    long id,
    short version,
    LocalDate asOf,
    Race category,
    Collection<Race> detailed
) {

  record Race(
      String id,
      String description
  ) {
  }
}
