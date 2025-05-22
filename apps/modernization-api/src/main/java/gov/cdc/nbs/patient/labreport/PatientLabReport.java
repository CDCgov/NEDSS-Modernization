package gov.cdc.nbs.patient.labreport;

import java.util.List;
import com.fasterxml.jackson.annotation.JsonProperty;

public record PatientLabReport(
    @JsonProperty(required = true) String eventId,
    @JsonProperty String receivedDate,
    @JsonProperty String processingDecision,
    @JsonProperty FacilityProviders facilityProviders,
    @JsonProperty String collectedDate,
    @JsonProperty List<TestResult> testResults,
    @JsonProperty AssociatedInvestigation associatedInvestigation,
    @JsonProperty String programArea,
    @JsonProperty(required = true) String jurisdiction,
    @JsonProperty long labIdentifier) {

  public record AssociatedInvestigation(
      @JsonProperty String investigationId,
      @JsonProperty String condition,
      @JsonProperty String status) {
  }

  public record TestResult(
      long observationUid,
      @JsonProperty long eventId,
      @JsonProperty String resultedTest,
      @JsonProperty String codedResult,
      @JsonProperty String numericResult,
      @JsonProperty String units,
      @JsonProperty String highRange,
      @JsonProperty String lowRange,
      @JsonProperty String statusDetails) {
  }

  public record FacilityProviders(
      @JsonProperty String reportingFacility,
      @JsonProperty String orderingProvider,
      @JsonProperty String sendingFacility) {
  }
}
