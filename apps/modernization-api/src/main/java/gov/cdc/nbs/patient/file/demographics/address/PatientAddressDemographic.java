package gov.cdc.nbs.patient.file.demographics.address;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import gov.cdc.nbs.data.selectable.Selectable;
import gov.cdc.nbs.time.json.FormattedLocalDateJsonDeserializer;

import java.time.LocalDate;

record PatientAddressDemographic(
    @JsonProperty(required = true)
    long identifier,
    @JsonProperty(required = true)
    @JsonDeserialize(using = FormattedLocalDateJsonDeserializer.class)
    LocalDate asOf,
    @JsonProperty(required = true)
    Selectable type,
    @JsonProperty(required = true)
    Selectable use,
    String address1,
    String address2,
    String city,
    Selectable state,
    String zipcode,
    Selectable county,
    String censusTract,
    Selectable country,
    String comment
) {
}
