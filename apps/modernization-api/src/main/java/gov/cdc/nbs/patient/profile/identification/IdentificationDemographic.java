package gov.cdc.nbs.patient.profile.identification;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import gov.cdc.nbs.time.json.FormattedLocalDateJsonDeserializer;

import java.time.LocalDate;

public record IdentificationDemographic(
    @JsonDeserialize(using = FormattedLocalDateJsonDeserializer.class) LocalDate asOf,
    String type,
    String issuer,
    String id
) {
}
