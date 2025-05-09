package gov.cdc.nbs.patient.investigation;

import com.fasterxml.jackson.annotation.JsonProperty;

public record PatientInvestigation(
    @JsonProperty(required = true) String investigationId,
    @JsonProperty(required = true) long identifier,
    @JsonProperty String startedOn,
    @JsonProperty(required = true) String condition,
    @JsonProperty(required = true) String status,
    @JsonProperty String caseStatus,
    @JsonProperty(required = true) String jurisdiction,
    @JsonProperty String coInfection,
    @JsonProperty String notification,
    @JsonProperty InvestigatorName investigatorName,
    @JsonProperty(required = true) boolean comparable) {

  public record InvestigatorName(
      @JsonProperty String first,
      @JsonProperty String last) {
  }
}
