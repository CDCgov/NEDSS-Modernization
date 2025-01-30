package gov.cdc.nbs.patient.profile.mortality.change;

import java.time.LocalDate;

record UpdatePatientMortality(
     long patient,
     LocalDate asOf,
     String deceased,
     LocalDate deceasedOn,
     String city,
     String state,
     String county,
     String country
 ) {
}
