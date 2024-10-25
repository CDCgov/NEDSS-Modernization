package gov.cdc.nbs.patient.search;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import gov.cdc.nbs.entity.enums.RecordStatus;
import gov.cdc.nbs.message.enums.Deceased;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Getter
@Setter
@EqualsAndHashCode
@ToString
@JsonInclude(Include.NON_NULL)
public class PatientFilter {

  @Getter
  @Setter
  @AllArgsConstructor
  @NoArgsConstructor
  @EqualsAndHashCode
  public static class Identification {
    private String identificationNumber;
    private String assigningAuthority;
    private String identificationType;
  }


  private String id;
  private String lastName;
  private String firstName;
  private String race;
  private Identification identification;
  private String phoneNumber;
  private String email;
  private LocalDate dateOfBirth;
  private String dateOfBirthOperator;
  private String gender;
  private Deceased deceased;
  private String address;
  private String city;
  private String state;
  private String country;
  private String zip;
  private String mortalityStatus;
  private String ethnicity;
  private List<RecordStatus> recordStatus;
  private String morbidityId;
  private String documentId;
  private String stateCaseId;
  private String abcCaseId;
  private String cityCountyCaseId;
  private String notificationId;
  private String treatmentId;
  private String vaccinationId;
  private String labReportId;
  private String accessionNumberId;

  private boolean disableSoundex;
  @JsonIgnore
  @Getter(AccessLevel.NONE)
  @Setter(AccessLevel.NONE)
  private List<RecordStatus> adjustedStatus;

  public PatientFilter() {
    this(RecordStatus.ACTIVE);
  }

  public PatientFilter(RecordStatus required, RecordStatus... recordStatus) {
    this.recordStatus = new ArrayList<>();
    this.recordStatus.add(required);
    if (recordStatus != null && recordStatus.length > 0) {
      Collections.addAll(this.recordStatus, recordStatus);
    }
  }

  public Identification getIdentification() {
    if (this.identification == null) {
      this.identification = new Identification();
    }
    return identification;
  }

  public List<RecordStatus> adjustedStatus() {
    return adjustedStatus == null ? List.copyOf(this.recordStatus) : List.copyOf(this.adjustedStatus);
  }

  public PatientFilter adjustStatuses(final Collection<RecordStatus> statuses) {
    this.adjustedStatus = List.copyOf(statuses);
    return this;
  }

  public PatientFilter withGender(final String gender) {
    this.gender = gender;
    return this;
  }

  public PatientFilter withId(final String id) {
    this.id = id;
    return this;
  }

  public PatientFilter withMorbidityId(final String identifier) {
    this.morbidityId = identifier;
    return this;
  }

  public Optional<String> maybeMorbidityId() {
    return Optional.ofNullable(morbidityId);
  }

  public PatientFilter withDocumentId(final String identifier) {
    this.documentId = identifier;
    return this;
  }

  public Optional<String> maybeDocumentId() {
    return Optional.ofNullable(documentId);
  }


  public PatientFilter withStateCaseId(final String identifier) {
    this.stateCaseId = identifier;
    return this;
  }

  public Optional<String> maybeStateCaseId() {
    return Optional.ofNullable(stateCaseId);
  }


  public PatientFilter withAbcCaseId(final String identifier) {
    this.abcCaseId = identifier;
    return this;
  }

  public Optional<String> maybeAbcCaseId() {
    return Optional.ofNullable(abcCaseId);
  }

  public PatientFilter withCityCountyCaseId(final String identifier) {
    this.cityCountyCaseId = identifier;
    return this;
  }

  public Optional<String> maybeCityCountyCaseId() {
    return Optional.ofNullable(cityCountyCaseId);
  }

  public PatientFilter withNotificationId(final String identifier) {
    this.notificationId = identifier;
    return this;
  }

  public Optional<String> maybeNotificationId() {
    return Optional.ofNullable(notificationId);
  }

  public PatientFilter withTreatmentId(final String identifier) {
    this.treatmentId = identifier;
    return this;
  }

  public Optional<String> maybeTreatmentId() {
    return Optional.ofNullable(treatmentId);
  }

  public PatientFilter withVaccinationId(final String identifier) {
    this.vaccinationId = identifier;
    return this;
  }

  public Optional<String> maybeVaccinationId() {
    return Optional.ofNullable(vaccinationId);
  }

  public PatientFilter withLabReportId(final String identifier) {
    this.labReportId = identifier;
    return this;
  }

  public Optional<String> maybeLabReportId() {
    return Optional.ofNullable(labReportId);
  }


}
