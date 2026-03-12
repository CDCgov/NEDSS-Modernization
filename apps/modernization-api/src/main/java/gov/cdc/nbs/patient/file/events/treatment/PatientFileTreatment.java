package gov.cdc.nbs.patient.file.events.treatment;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import gov.cdc.nbs.demographics.name.DisplayableSimpleName;
import gov.cdc.nbs.patient.events.investigation.association.AssociatedInvestigation;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collection;

@JsonInclude(JsonInclude.Include.NON_NULL)
record PatientFileTreatment(
    @JsonProperty(required = true) long patient,
    @JsonProperty(required = true) long id,
    @JsonProperty(required = true) String local,
    LocalDateTime createdOn,
    LocalDate treatedOn,
    String description,
    String organization,
    DisplayableSimpleName provider,
    Collection<AssociatedInvestigation> associations) {

  PatientFileTreatment withAssociations(final Collection<AssociatedInvestigation> associations) {
    return new PatientFileTreatment(
        patient,
        id,
        local,
        createdOn,
        treatedOn,
        description,
        organization,
        provider,
        associations);
  }
}
