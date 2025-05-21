package gov.cdc.nbs.patient.labreport;

import java.util.List;
import com.fasterxml.jackson.annotation.JsonProperty;

public record PatientLabReport(
    @JsonProperty(required = true) String eventId,
    @JsonProperty String receivedDate,
    @JsonProperty String processingDecision,
    @JsonProperty FacilityProviders facilityProviders,
    @JsonProperty String collectedDate,
    @JsonProperty TestResult testResult,
    @JsonProperty AssociatedInvestigation associatedInvestigation,
    @JsonProperty String programArea,
    @JsonProperty(required = true) String jurisdiction,
    @JsonProperty String labIdentifier) {

  public record AssociatedInvestigation(
      @JsonProperty String investigationId,
      @JsonProperty String condition,
      @JsonProperty String status) {
  }

  public record TestResult(
      @JsonProperty long eventId,
      @JsonProperty String resutedTest,
      @JsonProperty String codedResult,
      @JsonProperty String numericResult,
      @JsonProperty String units,
      @JsonProperty String range,
      @JsonProperty String statusDetails) {
  }

  public record FacilityProviders(
      @JsonProperty String reportingFacility,
      @JsonProperty String orderingProvider,
      @JsonProperty String sendingFacility) {
  }
}
