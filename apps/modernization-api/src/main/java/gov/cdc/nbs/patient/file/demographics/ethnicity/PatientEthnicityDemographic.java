package gov.cdc.nbs.patient.file.demographics.ethnicity;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import gov.cdc.nbs.data.selectable.Selectable;
import gov.cdc.nbs.time.json.FormattedLocalDateJsonDeserializer;

import java.time.LocalDate;
import java.util.Collection;

@JsonInclude(JsonInclude.Include.NON_NULL)
record PatientEthnicityDemographic(
    @JsonDeserialize(using = FormattedLocalDateJsonDeserializer.class) LocalDate asOf,
    Selectable ethnicGroup,
    Selectable unknownReason,
    Collection<Selectable> detailed
) {


}
