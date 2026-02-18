package gov.cdc.nbs.patient.file.events.document;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import gov.cdc.nbs.patient.events.investigation.association.AssociatedInvestigation;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Collections;

@JsonInclude(JsonInclude.Include.NON_NULL)
record PatientFileDocument(
    @JsonProperty(required = true) long patient,
    @JsonProperty(required = true) long id,
    @JsonProperty(required = true) String local,
    LocalDateTime receivedOn,
    String sendingFacility,
    LocalDate reportedOn,
    String condition,
    boolean updated,
    Collection<AssociatedInvestigation> associations) {

  PatientFileDocument(
      long patient,
      long id,
      String local,
      LocalDateTime receivedOn,
      String sendingFacility,
      LocalDate reportedOn,
      String condition,
      boolean updated) {
    this(
        patient,
        id,
        local,
        receivedOn,
        sendingFacility,
        reportedOn,
        condition,
        updated,
        Collections.emptyList());
  }

  PatientFileDocument withAssociations(final Collection<AssociatedInvestigation> associations) {
    return new PatientFileDocument(
        patient,
        id,
        local,
        receivedOn,
        sendingFacility,
        reportedOn,
        condition,
        updated,
        associations);
  }
}
