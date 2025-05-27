package gov.cdc.nbs.patient.file.summary.drr;

import gov.cdc.nbs.demographics.name.DisplayableSimpleName;

import java.time.LocalDateTime;
import java.util.List;

public record DocumentRequiringReview(
    Long id,
    String local,
    String type,
    LocalDateTime eventDate,
    LocalDateTime dateReceived,
    boolean isElectronic,
    boolean isUpdate,
    String reportingFacility,
    DisplayableSimpleName orderingProvider,
    String sendingFacility,
    String condition,
    List<String> treatments
) {

  public DocumentRequiringReview withTreatments(final List<String> treatments) {
    return new DocumentRequiringReview(
        id(),
        local(),
        type(),
        eventDate(),
        dateReceived(),
        isElectronic(),
        isUpdate(),
        reportingFacility(),
        orderingProvider(),
        sendingFacility(),
        condition(),
        treatments
    );
  }

}
