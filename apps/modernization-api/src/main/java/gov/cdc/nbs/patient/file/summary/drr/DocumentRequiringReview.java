package gov.cdc.nbs.patient.file.summary.drr;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import gov.cdc.nbs.demographics.name.DisplayableSimpleName;
import gov.cdc.nbs.patient.events.tests.ResultedTest;
import gov.cdc.nbs.patient.file.events.report.laboratory.PatientLabReport;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Collections;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record DocumentRequiringReview(
    @JsonProperty(required = true) long patient,
    @JsonProperty(required = true) long id,
    @JsonProperty(required = true) String local,
    @JsonProperty(required = true) String type,
    LocalDate eventDate,
    LocalDateTime dateReceived,
    boolean isElectronic,
    boolean isUpdate,
    String reportingFacility,
    String orderingFacility,
    DisplayableSimpleName orderingProvider,
    String sendingFacility,
    String condition,
    PatientLabReport.Specimen specimen,
    Collection<String> treatments,
    Collection<ResultedTest> resultedTests) {

  public DocumentRequiringReview(
      long patient,
      long id,
      String local,
      String type,
      LocalDate eventDate,
      LocalDateTime dateReceived,
      boolean isElectronic,
      boolean isUpdate,
      String reportingFacility,
      String orderingFacility,
      DisplayableSimpleName orderingProvider,
      String sendingFacility,
      String condition) {
    this(
        patient,
        id,
        local,
        type,
        eventDate,
        dateReceived,
        isElectronic,
        isUpdate,
        reportingFacility,
        orderingFacility,
        orderingProvider,
        sendingFacility,
        condition,
        null,
        Collections.emptyList(),
        Collections.emptyList());
  }

  public DocumentRequiringReview(
      long patient,
      long id,
      String local,
      String type,
      LocalDate eventDate,
      LocalDateTime dateReceived,
      boolean isElectronic,
      boolean isUpdate,
      String reportingFacility,
      String orderingFacility,
      DisplayableSimpleName orderingProvider,
      String sendingFacility,
      String condition,
      String specimentSite,
      String specimentSource) {
    this(
        patient,
        id,
        local,
        type,
        eventDate,
        dateReceived,
        isElectronic,
        isUpdate,
        reportingFacility,
        orderingFacility,
        orderingProvider,
        sendingFacility,
        condition,
        new PatientLabReport.Specimen(specimentSite, specimentSource),
        Collections.emptyList(),
        Collections.emptyList());
  }

  public DocumentRequiringReview withTreatments(final Collection<String> treatments) {
    return new DocumentRequiringReview(
        patient(),
        id(),
        local(),
        type(),
        eventDate(),
        dateReceived(),
        isElectronic(),
        isUpdate(),
        reportingFacility(),
        orderingFacility(),
        orderingProvider(),
        sendingFacility(),
        condition(),
        specimen(),
        treatments,
        resultedTests());
  }

  public DocumentRequiringReview withResultedTests(final Collection<ResultedTest> resultedTests) {
    return new DocumentRequiringReview(
        patient(),
        id(),
        local(),
        type(),
        eventDate(),
        dateReceived(),
        isElectronic(),
        isUpdate(),
        reportingFacility(),
        orderingFacility(),
        orderingProvider(),
        sendingFacility(),
        condition(),
        specimen(),
        treatments(),
        resultedTests);
  }

}
