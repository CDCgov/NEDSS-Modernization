package gov.cdc.nbs.patient.investigation;

import com.fasterxml.jackson.annotation.JsonProperty;

public record PatientInvestigation(
    @JsonProperty(required = true) String investigationId,
    @JsonProperty(required = true) long identifier,
    @JsonProperty(required = true) String startedOn,
    @JsonProperty(required = true) String condition,
    @JsonProperty(required = true) String status,
    @JsonProperty(required = true) String caseStatus,
    @JsonProperty(required = true) String jurisdiction,
    @JsonProperty(required = true) String coInfection,
    @JsonProperty(required = true) String notification,
    @JsonProperty(required = true) InvestigatorName investigatorName,
    @JsonProperty(required = true) boolean comparable) {

  public record InvestigatorName(
      @JsonProperty(required = true) String first,
      @JsonProperty(required = true) String last) {
  }
}


