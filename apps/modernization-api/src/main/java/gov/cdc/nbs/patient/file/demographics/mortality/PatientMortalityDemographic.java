package gov.cdc.nbs.patient.file.demographics.mortality;

import com.fasterxml.jackson.annotation.JsonInclude;
import gov.cdc.nbs.data.selectable.Selectable;
import java.time.LocalDate;

@JsonInclude(JsonInclude.Include.NON_NULL)
record PatientMortalityDemographic(
    LocalDate asOf,
    Selectable deceased,
    LocalDate deceasedOn,
    String city,
    Selectable state,
    Selectable county,
    Selectable country) {}
