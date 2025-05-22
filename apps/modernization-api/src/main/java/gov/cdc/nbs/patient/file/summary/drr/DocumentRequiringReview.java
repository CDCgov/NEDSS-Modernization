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
    List<Description> descriptions

) {

  public record Description(
      String title,
      String value
  ) {
    public Description(String title) {
      this(title, "");
    }
  }

}
