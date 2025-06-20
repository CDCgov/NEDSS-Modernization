package gov.cdc.nbs.patient.file.demographics.sex_birth;

import com.fasterxml.jackson.annotation.JsonInclude;
import gov.cdc.nbs.data.selectable.Selectable;

import java.time.LocalDate;

@JsonInclude(JsonInclude.Include.NON_NULL)
record PatientSexBirthDemographic(
    LocalDate asOf,
    LocalDate bornOn,
    Selectable sex,
    Selectable multiple,
    Integer order,
    String city,
    Selectable state,
    Selectable county,
    Selectable country,
    Selectable current,
    Selectable unknownReason,
    Selectable transgenderInformation,
    String additionalGender
) {
}
