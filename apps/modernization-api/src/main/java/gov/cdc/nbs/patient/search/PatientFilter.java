package gov.cdc.nbs.patient.search;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import gov.cdc.nbs.entity.enums.RecordStatus;
import gov.cdc.nbs.message.enums.Deceased;
import gov.cdc.nbs.search.criteria.date.DateCriteria;
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
  @JsonInclude(Include.NON_NULL)
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
  private String morbidity;
  private String document;
  private String stateCase;
  private String abcCase;
  private String cityCountyCase;
  private String notification;
  private String treatment;
  private String vaccination;
  private String investigation;
  private String labReport;
  private String accessionNumber;

  private boolean disableSoundex;
  @JsonIgnore
  @Getter(AccessLevel.NONE)
  @Setter(AccessLevel.NONE)
  private List<RecordStatus> adjustedStatus;

  private DateCriteria bornOn;

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

  public PatientFilter withMorbidity(final String identifier) {
    this.morbidity = identifier;
    return this;
  }

  public Optional<String> maybeMorbidity() {
    return Optional.ofNullable(morbidity);
  }

  public PatientFilter withDocument(final String identifier) {
    this.document = identifier;
    return this;
  }

  public Optional<String> maybeDocument() {
    return Optional.ofNullable(document);
  }


  public PatientFilter withStateCase(final String identifier) {
    this.stateCase = identifier;
    return this;
  }

  public Optional<String> maybeStateCase() {
    return Optional.ofNullable(stateCase);
  }


  public PatientFilter withAbcCase(final String identifier) {
    this.abcCase = identifier;
    return this;
  }

  public Optional<String> maybeAbcCase() {
    return Optional.ofNullable(abcCase);
  }

  public PatientFilter withCityCountyCase(final String identifier) {
    this.cityCountyCase = identifier;
    return this;
  }

  public Optional<String> maybeCityCountyCase() {
    return Optional.ofNullable(cityCountyCase);
  }

  public PatientFilter withNotification(final String identifier) {
    this.notification = identifier;
    return this;
  }

  public Optional<String> maybeNotification() {
    return Optional.ofNullable(notification);
  }

  public PatientFilter withTreatment(final String identifier) {
    this.treatment = identifier;
    return this;
  }

  public Optional<String> maybeTreatment() {
    return Optional.ofNullable(treatment);
  }

  public PatientFilter withVaccination(final String identifier) {
    this.vaccination = identifier;
    return this;
  }

  public Optional<String> maybeVaccination() {
    return Optional.ofNullable(vaccination);
  }

  public PatientFilter withInvestigation(final String identifier) {
    this.investigation = identifier;
    return this;
  }

  public Optional<String> maybeInvestigation() {
    return Optional.ofNullable(investigation);
  }

  public PatientFilter withLabReport(final String identifier) {
    this.labReport = identifier;
    return this;
  }

  public Optional<String> maybeLabReport() {
    return Optional.ofNullable(labReport);
  }

  public PatientFilter withAccessiontNumber(final String identifier) {
    this.accessionNumber = identifier;
    return this;
  }

  public Optional<String> maybeAccessionNumber() {
    return Optional.ofNullable(accessionNumber);
  }

  public PatientFilter withBornOnDay(final int day) {
    if (this.bornOn != null) {
      this.bornOn = this.bornOn.withEquals(this.bornOn.equals().withDay(day));
    } else {
      this.bornOn = DateCriteria.equals(day, null, null);
    }
    return this;
  }

  public PatientFilter withBornOnMonth(final int month) {
    if (this.bornOn != null) {
      this.bornOn = this.bornOn.withEquals(this.bornOn.equals().withMonth(month));
    } else {
      this.bornOn = DateCriteria.equals(null, month, null);
    }
    return this;
  }

  public PatientFilter withBornOnYear(final int year) {
    if (this.bornOn != null) {
      this.bornOn = this.bornOn.withEquals(this.bornOn.equals().withYear(year));
    } else {
      this.bornOn = DateCriteria.equals(null, null, year);
    }
    return this;
  }

  public PatientFilter withBornBetween(final LocalDate from, final LocalDate to) {
    this.bornOn = DateCriteria.between(from, to);
    return this;
  }
}
