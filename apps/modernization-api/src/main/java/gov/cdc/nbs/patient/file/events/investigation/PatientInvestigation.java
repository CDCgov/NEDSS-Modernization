package gov.cdc.nbs.patient.file.events.investigation;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import gov.cdc.nbs.demographics.name.DisplayableSimpleName;
import java.time.LocalDate;

@JsonInclude(JsonInclude.Include.NON_NULL)
record PatientInvestigation(
    @JsonProperty(required = true) long patient,
    @JsonProperty(required = true) long identifier,
    @JsonProperty(required = true) String local,
    @JsonProperty LocalDate startedOn,
    @JsonProperty(required = true) String condition,
    @JsonProperty(required = true) String status,
    String caseStatus,
    @JsonProperty(required = true) String jurisdiction,
    String coInfection,
    String notification,
    DisplayableSimpleName investigator,
    @JsonProperty(required = true) boolean comparable) {}
