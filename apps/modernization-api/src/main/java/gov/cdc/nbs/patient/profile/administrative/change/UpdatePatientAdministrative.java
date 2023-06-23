package gov.cdc.nbs.patient.profile.administrative.change;

import java.time.Instant;
import java.time.LocalDate;

record UpdatePatientAdministrative(
     long patient,
     Instant asOf,
     String comment
 ) {
}
