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
    MotherInformation mother,
    Collection<AssociatedInvestigation> associations) {
  PatientFileBirthRecord(
      long patient,
      long identifier,
      String local,
      LocalDateTime receivedOn,
      String facility,
      LocalDate collectedOn,
      String certificate,
      MotherInformation mother) {

    this(
        patient,
        identifier,
        local,
        receivedOn,
        facility,
        collectedOn,
        certificate,
        mother,
        Collections.emptyList());
  }

  @JsonInclude(JsonInclude.Include.NON_NULL)
  record MotherInformation(Name name, Address address) {

    @JsonInclude(JsonInclude.Include.NON_NULL)
    record Name(String first, String middle, String last, String suffix) {}

    @JsonInclude(JsonInclude.Include.NON_NULL)
    record Address(
        String address,
        String address2,
        String city,
        String state,
        String county,
        String zipcode) {}
  }

  PatientFileBirthRecord withMother(final MotherInformation mother) {
    return new PatientFileBirthRecord(
        patient, id, local, receivedOn, facility, collectedOn, certificate, mother, associations);
  }

  PatientFileBirthRecord withAssociations(final Collection<AssociatedInvestigation> associations) {
    return new PatientFileBirthRecord(
        patient, id, local, receivedOn, facility, collectedOn, certificate, mother, associations);
  }
}
