package gov.cdc.nbs.patient.file.events.vaccination;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import gov.cdc.nbs.demographics.name.DisplayableSimpleName;
import gov.cdc.nbs.patient.events.investigation.association.AssociatedInvestigation;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collection;

@JsonInclude(JsonInclude.Include.NON_NULL)
record PatientVaccination(
    @JsonProperty(required = true) long patient,
    @JsonProperty(required = true) long id,
    @JsonProperty(required = true) String local,
    LocalDateTime createdOn,
    String organization,
    DisplayableSimpleName provider,
    LocalDate administeredOn,
    String administered,
    Collection<AssociatedInvestigation> associations) {
  PatientVaccination withAssociations(final Collection<AssociatedInvestigation> associations) {
    return new PatientVaccination(
        patient,
        id,
        local,
        createdOn,
        organization,
        provider,
        administeredOn,
        administered,
        associations);
  }
}
