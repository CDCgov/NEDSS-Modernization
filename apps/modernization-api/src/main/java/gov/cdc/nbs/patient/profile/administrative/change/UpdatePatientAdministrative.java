package gov.cdc.nbs.patient.profile.administrative.change;

import java.time.Instant;

record UpdatePatientAdministrative(
     long patient,
     Instant asOf,
     String comment
 ) {
}
