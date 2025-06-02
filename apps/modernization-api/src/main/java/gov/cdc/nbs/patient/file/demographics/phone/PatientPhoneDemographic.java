package gov.cdc.nbs.patient.file.demographics.phone;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import gov.cdc.nbs.data.selectable.Selectable;
import gov.cdc.nbs.time.json.FormattedLocalDateJsonDeserializer;

import java.time.LocalDate;

@JsonInclude(JsonInclude.Include.NON_NULL)
record PatientPhoneDemographic(
    @JsonProperty(required = true)
    long identifier,
    @JsonProperty(required = true)
    @JsonDeserialize(using = FormattedLocalDateJsonDeserializer.class) LocalDate asOf,
    @JsonProperty(required = true)
    Selectable type,
    @JsonProperty(required = true)
    Selectable use,
    String countryCode,
    String phoneNumber,
    String extension,
    String email,
    String url,
    String comment
) {
}
