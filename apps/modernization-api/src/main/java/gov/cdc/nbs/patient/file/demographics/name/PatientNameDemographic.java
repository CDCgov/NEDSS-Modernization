package gov.cdc.nbs.patient.file.demographics.name;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import gov.cdc.nbs.data.selectable.Selectable;
import gov.cdc.nbs.time.json.FormattedLocalDateJsonDeserializer;

import java.time.LocalDate;

@JsonInclude(JsonInclude.Include.NON_NULL)
record PatientNameDemographic(
    @JsonProperty(required = true)
    short sequence,
    @JsonProperty(required = true)
    @JsonDeserialize(using = FormattedLocalDateJsonDeserializer.class)
    LocalDate asOf,
    @JsonProperty(required = true)
    Selectable type,
    Selectable prefix,
    String first,
    String middle,
    String secondMiddle,
    String last,
    String secondLast,
    Selectable suffix,
    Selectable degree) {
}
