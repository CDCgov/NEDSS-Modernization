package gov.cdc.nbs.patient.labreport;

import java.util.List;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

public record PatientLabReport(
    @JsonProperty(required = true) String eventId,
    String receivedDate,
    String processingDecision,
    FacilityProviders facilityProviders,
    String collectedDate,
    List<TestResult> testResults,
    AssociatedInvestigation associatedInvestigation,
    String programArea,
    @JsonProperty(required = true) String jurisdiction,
    long id) {

  public record AssociatedInvestigation(
      String id,
      String condition,
      String status) {
  }

  public record TestResult(
      @JsonIgnore long observationUid,
      @JsonIgnore long eventId,
      String resultedTest,
      String codedResult,
      String numericResult,
      String units,
      String highRange,
      String lowRange,
      String statusDetails) {
  }

  public record FacilityProviders(
      String reportingFacility,
      OrderingProvider orderingProvider,
      String sendingFacility) {
  }

  public record OrderingProvider(
      String prefix,
      String first,
      String last,
      String suffix) {
  }
}
