package gov.cdc.nbs.patient.file.events.contact;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
record NamedContact(
    @JsonProperty(required = true) long patientId,
    String first,
    String middle,
    String last,
    String suffix) {}
