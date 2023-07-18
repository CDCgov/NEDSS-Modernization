package gov.cdc.nbs.patient.profile.general.change;

import java.time.Instant;

record UpdateGeneralInformation(
     long patient,
     Instant asOf,
     String maritalStatus,
     String maternalMaidenName,
     Integer adultsInHouse,
     Integer childrenInHouse,
     String occupation,
     String educationLevel,
     String primaryLanguage,
     String speaksEnglish,
     String stateHIVCase
 ) {
}
