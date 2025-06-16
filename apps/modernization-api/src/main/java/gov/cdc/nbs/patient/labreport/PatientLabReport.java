package gov.cdc.nbs.patient.labreport;

import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import gov.cdc.nbs.demographics.name.DisplayableSimpleName;
import gov.cdc.nbs.patient.events.tests.ResultedTest;

public record PatientLabReport(
    @JsonProperty(required = true) String eventId,
    LocalDateTime receivedDate,
    String processingDecision,
    LocalDateTime collectedDate,
    List<ResultedTest> testResults,
    AssociatedInvestigation associatedInvestigation,
    String programArea,
    @JsonProperty(required = true) String jurisdiction,
    long id,
    String specimenSource,
    String reportingFacility,
    DisplayableSimpleName orderingProvider,
    String orderingFacility) {

  public record AssociatedInvestigation(
      String id,
      String condition,
      String local,
      String status) {
  }
}
