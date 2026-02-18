package gov.cdc.nbs.patient.file.events.report.morbidity;

import com.fasterxml.jackson.annotation.JsonProperty;
import gov.cdc.nbs.demographics.name.DisplayableSimpleName;
import gov.cdc.nbs.patient.events.investigation.association.AssociatedInvestigation;
import gov.cdc.nbs.patient.events.tests.ResultedTest;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collection;

record PatientMorbidityReport(
    @JsonProperty(required = true) long patient,
    @JsonProperty(required = true) long id,
    @JsonProperty(required = true) String local,
    @JsonProperty(required = true) String jurisdiction,
    @JsonProperty(required = true) LocalDateTime addedOn,
    LocalDateTime receivedOn,
    LocalDate reportedOn,
    String condition,
    String processingDecision,
    String reportingFacility,
    DisplayableSimpleName orderingProvider,
    DisplayableSimpleName reportingProvider,
    Collection<String> treatments,
    Collection<ResultedTest> resultedTests,
    Collection<AssociatedInvestigation> associations) {

  public PatientMorbidityReport withTreatments(final Collection<String> treatments) {
    return new PatientMorbidityReport(
        patient,
        id,
        local,
        jurisdiction,
        addedOn,
        receivedOn,
        reportedOn,
        condition,
        processingDecision,
        reportingFacility,
        orderingProvider,
        reportingProvider,
        treatments,
        resultedTests,
        associations);
  }

  public PatientMorbidityReport withResultedTests(final Collection<ResultedTest> resultedTests) {
    return new PatientMorbidityReport(
        patient,
        id,
        local,
        jurisdiction,
        addedOn,
        receivedOn,
        reportedOn,
        condition,
        processingDecision,
        reportingFacility,
        orderingProvider,
        reportingProvider,
        treatments,
        resultedTests,
        associations);
  }

  public PatientMorbidityReport withAssociations(
      final Collection<AssociatedInvestigation> associations) {
    return new PatientMorbidityReport(
        patient,
        id,
        local,
        jurisdiction,
        addedOn,
        receivedOn,
        reportedOn,
        condition,
        processingDecision,
        reportingFacility,
        orderingProvider,
        reportingProvider,
        treatments,
        resultedTests,
        associations);
  }
}
