package gov.cdc.nbs.patient.profile.mortality.change;

import java.time.Instant;
import java.time.LocalDate;

record UpdatePatientMortality(
     long patient,
     Instant asOf,
     String deceased,
     LocalDate deceasedOn,
     String city,
     String state,
     String county,
     String country
 ) {
}
