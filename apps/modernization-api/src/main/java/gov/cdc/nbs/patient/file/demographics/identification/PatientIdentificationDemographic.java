package gov.cdc.nbs.patient.file.demographics.identification;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import gov.cdc.nbs.data.selectable.Selectable;
import gov.cdc.nbs.time.json.FormattedLocalDateJsonDeserializer;

import java.time.LocalDate;

@JsonInclude(JsonInclude.Include.NON_NULL)
record PatientIdentificationDemographic(
    @JsonProperty(required = true)
    long identifier,
    @JsonProperty(required = true)
    @JsonDeserialize(using = FormattedLocalDateJsonDeserializer.class) LocalDate asOf,
    @JsonProperty(required = true)
    Selectable type,
    Selectable issuer,
    @JsonProperty(required = true)
    String value
) {
}
