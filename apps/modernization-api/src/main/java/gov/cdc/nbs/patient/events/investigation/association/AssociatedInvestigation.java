package gov.cdc.nbs.patient.events.investigation.association;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record AssociatedInvestigation(
    @JsonProperty(required = true) long id,
    @JsonProperty(required = true) String local,
    @JsonProperty(required = true) String condition,
    String status) {}
