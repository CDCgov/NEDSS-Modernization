package gov.cdc.nbs.patient.file.summary.drr;

import gov.cdc.nbs.demographics.name.DisplayableSimpleName;
import gov.cdc.nbs.patient.events.tests.ResultedTest;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Collections;

public record DocumentRequiringReview(
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
    Collection<String> treatments,
    Collection<ResultedTest> resultedTests
) {

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
      String condition
  ) {
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
        Collections.emptyList(),
        Collections.emptyList()
    );
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
        treatments,
        resultedTests()
    );
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
        treatments(),
        resultedTests
    );
  }

}
