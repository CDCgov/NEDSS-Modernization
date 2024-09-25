package gov.cdc.nbs.patient.profile.create;

import com.fasterxml.jackson.annotation.JsonProperty;

public record CreatedPatient(
    @JsonProperty(required = true)
    long id,
    @JsonProperty(required = true)
    long shortId,
    @JsonProperty(required = true)
    String local
) {
}
