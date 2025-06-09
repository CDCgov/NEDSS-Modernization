package gov.cdc.nbs.patient.labreport;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import gov.cdc.nbs.patient.events.tests.ResultedTest;

public record PatientLabReport(
    @JsonProperty(required = true) String eventId,
    String receivedDate,
    String processingDecision,
    FacilityProviders facilityProviders,
    String collectedDate,
    List<ResultedTest> testResults,
    AssociatedInvestigation associatedInvestigation,
    String programArea,
    @JsonProperty(required = true) String jurisdiction,
    long id,
    String specimenSource) {

  public record AssociatedInvestigation(
      String id,
      String condition,
      String local,
      String status) {
  }


  public record FacilityProviders(
      String reportingFacility,
      OrderingProvider orderingProvider,
      String orderingFacility) {
  }


  public record OrderingProvider(
      String prefix,
      String first,
      String last,
      String suffix) {
  }
}
