package gov.cdc.nbs.patient.file.demographics.general;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import gov.cdc.nbs.data.selectable.Selectable;
import gov.cdc.nbs.data.sensitive.SensitiveValue;
import gov.cdc.nbs.time.json.FormattedLocalDateJsonDeserializer;

import java.time.LocalDate;

@JsonInclude(JsonInclude.Include.NON_NULL)
record PatientGeneralInformationDemographic(
    @JsonDeserialize(using = FormattedLocalDateJsonDeserializer.class)
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
