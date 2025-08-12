package gov.cdc.nbs.patient.file.events.record.birth;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import gov.cdc.nbs.patient.events.investigation.association.AssociatedInvestigation;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Collections;

@JsonInclude(JsonInclude.Include.NON_NULL)
record PatientFileBirthRecord(
    @JsonProperty(required = true) long patient,
    @JsonProperty(required = true) long id,
    @JsonProperty(required = true) String local,
    LocalDateTime receivedOn,
    String facility,
    LocalDate collectedOn,
    String certificate,
    Collection<AssociatedInvestigation> associations
) {
  PatientFileBirthRecord(
      long patient,
      long identifier,
      String local,
      LocalDateTime receivedOn,
      String facility,
      LocalDate collectedOn,
      String certificate

  ) {
    this(patient, identifier, local, receivedOn, facility, collectedOn, certificate, Collections.emptyList());
  }


  PatientFileBirthRecord withAssociations(final Collection<AssociatedInvestigation> associations) {
    return new PatientFileBirthRecord(
        patient,
        id,
        local,
        receivedOn,
        facility,
        collectedOn,
        certificate,
        associations
    );
  }
}
