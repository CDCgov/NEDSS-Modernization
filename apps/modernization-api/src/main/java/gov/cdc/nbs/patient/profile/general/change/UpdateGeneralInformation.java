package gov.cdc.nbs.patient.profile.general.change;

import java.time.LocalDate;

record UpdateGeneralInformation(
     long patient,
     LocalDate asOf,
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
