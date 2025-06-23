package gov.cdc.nbs.patient.profile.general;

import gov.cdc.nbs.data.sensitive.SensitiveValue;
import gov.cdc.nbs.message.enums.Indicator;

import java.time.LocalDate;

record PatientGeneral(
    long patient,
    long id,
    short version,
    LocalDate asOf,
    MaritalStatus maritalStatus,
    String maternalMaidenName,
    Integer adultsInHouse,
    Integer childrenInHouse,
    Occupation occupation,
    EducationLevel educationLevel,
    Language primaryLanguage,
    Indicator speaksEnglish,
    SensitiveValue stateHIVCase
) {

    record MaritalStatus(String id, String description){}

    record Occupation(String id, String description){}
    record EducationLevel(String id, String description){}

    record Language(String id, String description){}
}
