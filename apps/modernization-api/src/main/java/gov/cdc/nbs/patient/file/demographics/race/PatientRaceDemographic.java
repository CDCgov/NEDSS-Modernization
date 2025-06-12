package gov.cdc.nbs.patient.file.demographics.race;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import gov.cdc.nbs.data.selectable.Selectable;

import java.time.LocalDate;
import java.util.Collection;

@JsonInclude(JsonInclude.Include.NON_NULL)
record PatientRaceDemographic(
    @JsonProperty(required = true)
    LocalDate asOf,
    @JsonProperty(required = true)
    Selectable race,
    Collection<Selectable> detailed
) {
}
