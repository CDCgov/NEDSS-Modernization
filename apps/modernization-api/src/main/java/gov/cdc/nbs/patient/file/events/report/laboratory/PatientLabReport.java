package gov.cdc.nbs.patient.file.events.report.laboratory;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import gov.cdc.nbs.demographics.name.DisplayableSimpleName;
import gov.cdc.nbs.patient.events.investigation.association.AssociatedInvestigation;
import gov.cdc.nbs.patient.events.tests.ResultedTest;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collection;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record PatientLabReport(
    @JsonProperty(required = true) long patient,
    @JsonProperty(required = true) long id,
    @JsonProperty(required = true) String local,
    @JsonProperty(required = true) String programArea,
    @JsonProperty(required = true) String jurisdiction,
    LocalDateTime receivedDate,
    @JsonProperty(required = true)
    boolean electronic,
    String processingDecision,
    LocalDate collectedDate,
    Collection<ResultedTest> resultedTests,
    String reportingFacility,
    DisplayableSimpleName orderingProvider,
    String orderingFacility,
    Specimen specimen,
    Collection<AssociatedInvestigation> associations
) {

  public record Specimen(String site, String source) {
  }



  PatientLabReport withResultedTests(final Collection<ResultedTest> resultedTests) {
    return new PatientLabReport(
        patient(),
        id(),
        local(),
        programArea(),
        jurisdiction(),
        receivedDate(),
        electronic(),
        processingDecision(),
        collectedDate(),
        resultedTests,
        reportingFacility(),
        orderingProvider(),
        orderingFacility(),
        specimen(),
        associations()
    );
  }

  PatientLabReport withAssociations(final Collection<AssociatedInvestigation> associations) {
    return new PatientLabReport(
        patient(),
        id(),
        local(),
        programArea(),
        jurisdiction(),
        receivedDate(),
        electronic(),
        processingDecision(),
        collectedDate(),
        resultedTests(),
        reportingFacility(),
        orderingProvider(),
        orderingFacility(),
        specimen(),
        associations
    );
  }
}
