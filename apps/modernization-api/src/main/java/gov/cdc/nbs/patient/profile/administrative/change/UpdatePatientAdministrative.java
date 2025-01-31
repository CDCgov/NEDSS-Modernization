package gov.cdc.nbs.patient.profile.administrative.change;

import java.time.LocalDate;

record UpdatePatientAdministrative(
     long patient,
     LocalDate asOf,
     String comment
 ) {
}
