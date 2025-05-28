package gov.cdc.nbs.patient.events.tests;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record ResultedTest(
    @JsonProperty(required = true)
    String name,
    String result,
    String reference
) {
}
