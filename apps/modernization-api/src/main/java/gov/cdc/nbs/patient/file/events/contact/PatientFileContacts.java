package gov.cdc.nbs.patient.file.events.contact;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import gov.cdc.nbs.patient.events.investigation.association.AssociatedInvestigation;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collection;

@JsonInclude(JsonInclude.Include.NON_NULL)
record PatientFileContacts(
    @JsonProperty(required = true) String condition,
    @JsonProperty(required = true) Collection<PatientFileContact> contacts
) {

  @JsonInclude(JsonInclude.Include.NON_NULL)
  record PatientFileContact(
      @JsonProperty(required = true) long patient,
      @JsonProperty(required = true) long identifier,
      @JsonProperty(required = true) String local,
      @JsonProperty(required = true)
      String condition,
      String processingDecision,
      String referralBasis,
      @JsonProperty(required = true)
      LocalDateTime createdOn,
      LocalDate namedOn,
      @JsonProperty(required = true)
      NamedContact named,
      String priority,
      String disposition,
      AssociatedInvestigation associated
  ) {
  }

}
