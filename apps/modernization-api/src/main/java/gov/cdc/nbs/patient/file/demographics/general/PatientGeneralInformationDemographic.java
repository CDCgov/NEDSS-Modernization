package gov.cdc.nbs.patient.file.demographics.general;

import com.fasterxml.jackson.annotation.JsonInclude;
import gov.cdc.nbs.data.selectable.Selectable;
import gov.cdc.nbs.data.sensitive.SensitiveValue;

import java.time.LocalDate;

@JsonInclude(JsonInclude.Include.NON_NULL)
record PatientGeneralInformationDemographic(
    LocalDate asOf,
    Selectable maritalStatus,
    String maternalMaidenName,
    Integer adultsInResidence,
    Integer childrenInResidence,
    Selectable primaryOccupation,
    Selectable educationLevel,
    Selectable primaryLanguage,
    Selectable speaksEnglish,
    SensitiveValue stateHIVCase
) {

}
