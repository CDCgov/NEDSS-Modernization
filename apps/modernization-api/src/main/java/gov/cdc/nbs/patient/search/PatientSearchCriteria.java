package gov.cdc.nbs.patient.search;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import gov.cdc.nbs.entity.enums.RecordStatus;
import gov.cdc.nbs.message.enums.Deceased;
import gov.cdc.nbs.search.criteria.date.DateCriteria;
import gov.cdc.nbs.search.criteria.text.TextCriteria;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.*;

@Getter
@Setter
@JsonInclude(Include.NON_NULL)
public class PatientSearchCriteria {

  @JsonInclude(Include.NON_NULL)
  public record Identification(
      String identificationNumber,
      String assigningAuthority,
      String identificationType
  ) {
    Identification withType(final String type) {
      return new Identification(identificationNumber(), assigningAuthority(), type);
    }

    Identification withValue(final String value) {
      return new Identification(value, assigningAuthority(), identificationType());
    }
  }


  public record Filter(
      String id,
      String name,
      String ageOrDateOfBirth,
      String sex,
      String address,
      String email,
      String phone,
      String identification
  ) {
    Filter withId(final String id) {
      return new Filter(id, name(), ageOrDateOfBirth(), sex(), address(), email(), null, null);
    }

    Filter withName(final String name) {
      return new Filter(id(), name, ageOrDateOfBirth(), sex(), address(), email(), null, null);
    }

    Filter withAgeOrDateOfBirth(final String ageOrDateOfBirth) {
      return new Filter(id(), name(), ageOrDateOfBirth, sex(), address(), email(), null, null);
    }

    Filter withSex(final String sex) {
      return new Filter(id(), name(), ageOrDateOfBirth(), sex, address(), email(), null,
          null);
    }

    Filter withAddress(final String address) {
      return new Filter(id(), name(), ageOrDateOfBirth(), sex(), address, email(), null, null);
    }

    Filter withEmail(final String email) {
      return new Filter(id(), name(), ageOrDateOfBirth(), sex(), address(), email, null, null);
    }

    Filter withPhone(final String phone) {
      return new Filter(id(), name(), ageOrDateOfBirth(), sex(), address(), email(), phone, null);
    }

    Filter withIdentification(final String identification) {
      return new Filter(id(), name(), ageOrDateOfBirth(), sex(), address(), email(), phone(), identification);
    }

  }


  public record NameCriteria(TextCriteria first, TextCriteria last) {

    Optional<TextCriteria> maybeLast() {
      return Optional.ofNullable(last());
    }

    NameCriteria withLast(final TextCriteria last) {
      return new NameCriteria(first(), last);
    }

    Optional<TextCriteria> maybeFirst() {
      return Optional.ofNullable(first());
    }

    NameCriteria withFirst(final TextCriteria first) {
      return new NameCriteria(first, last());
    }
  }


  public record LocationCriteria(TextCriteria street, TextCriteria city) {

    Optional<TextCriteria> maybeStreet() {
      return Optional.ofNullable(street());
    }

    Optional<TextCriteria> maybeCity() {
      return Optional.ofNullable(city());
    }

    LocationCriteria withStreet(final TextCriteria street) {
      return new LocationCriteria(street, city());
    }

    LocationCriteria withCity(final TextCriteria city) {
      return new LocationCriteria(street(), city);
    }
  }


  private String id;
  private NameCriteria name;
  private String lastName;
  private String firstName;
  private String race;
  private Identification identification;
  private String phoneNumber;
  private String email;
  private LocalDate dateOfBirth;
  private String dateOfBirthOperator;
  private SearchableGender gender;
  private Deceased deceased;
  private LocationCriteria location;
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
  private Filter filter;

  private boolean disableSoundex;
  @JsonIgnore
  @Getter(AccessLevel.NONE)
  @Setter(AccessLevel.NONE)
  private List<RecordStatus> adjustedStatus;

  private DateCriteria bornOn;

  public PatientSearchCriteria() {
    this(RecordStatus.ACTIVE);
  }

  public PatientSearchCriteria(RecordStatus required, RecordStatus... recordStatus) {
    this.recordStatus = new ArrayList<>();
    this.recordStatus.add(required);
    if (recordStatus != null && recordStatus.length > 0) {
      Collections.addAll(this.recordStatus, recordStatus);
    }
  }

  public Identification getIdentification() {
    if (this.identification == null) {
      this.identification = new Identification(null, null, null);
    }
    return identification;
  }

  public PatientSearchCriteria withIdentification(final Identification identification) {
    this.identification = identification;
    return this;
  }

  public Filter getFilter() {
    if (this.filter == null) {
      this.filter = new Filter(null, null, null, null, null, null, null, null);
    }
    return filter;
  }

  public Optional<Filter> maybeFilter() {
    return Optional.ofNullable(this.filter);
  }

  public List<RecordStatus> adjustedStatus() {
    return adjustedStatus == null ? List.copyOf(this.recordStatus) : List.copyOf(this.adjustedStatus);
  }

  public PatientSearchCriteria adjustStatuses(final Collection<RecordStatus> statuses) {
    this.adjustedStatus = List.copyOf(statuses);
    return this;
  }

  public PatientSearchCriteria withGender(final String gender) {
    this.gender = SearchableGender.resolve(gender);
    return this;
  }

  public String getGender() {
    return gender == null ? null : gender.value();
  }

  public void setGender(final String gender) {
    this.gender = SearchableGender.resolve(gender);
  }

  public Optional<SearchableGender> maybeGender() {
    return Optional.ofNullable(gender);
  }

  public PatientSearchCriteria withId(final String id) {
    this.id = id;
    return this;
  }

  public PatientSearchCriteria withIdFilter(final String idFilter) {
    if (this.filter == null) {
      this.filter = new Filter(idFilter, null, null, null, null, null, null, null);
    } else {
      this.filter = this.filter.withId(idFilter);
    }
    return this;
  }

  public PatientSearchCriteria withNameFilter(final String nameFilter) {
    if (this.filter == null) {
      this.filter = new Filter(null, nameFilter, null, null, null, null, null, null);
    } else {
      this.filter = this.filter.withName(nameFilter);
    }
    return this;

  }

  public PatientSearchCriteria withAddressFilter(final String addressFilter) {
    if (this.filter == null) {
      this.filter = new Filter(null, null, null, null, addressFilter, null, null, null);
    } else {
      this.filter = this.filter.withAddress(addressFilter);
    }
    return this;

  }

  public PatientSearchCriteria withEmailFilter(final String emailFilter) {
    if (this.filter == null) {
      this.filter = new Filter(null, null, null, null, null, emailFilter, null, null);
    } else {
      this.filter = this.filter.withEmail(emailFilter);
    }
    return this;

  }

  public PatientSearchCriteria withPhoneFilter(final String phoneFilter) {
    if (this.filter == null) {
      this.filter = new Filter(null, null, null, null, null, null, phoneFilter, null);
    } else {
      this.filter = this.filter.withPhone(phoneFilter);
    }
    return this;

  }

  public PatientSearchCriteria withAgeOrDateOfBirthFilter(final String ageOrDateOfBirthFilter) {
    if (this.filter == null) {
      this.filter = new Filter(null, null, ageOrDateOfBirthFilter, null, null, null, null, null);
    } else {
      this.filter = this.filter.withAgeOrDateOfBirth(ageOrDateOfBirthFilter);
    }
    return this;

  }

  public PatientSearchCriteria withSexFilter(final String sex) {
    if (this.filter == null) {
      this.filter = new Filter(null, null, null, sex, null, null, null, null);
    } else {
      this.filter = this.filter.withSex(sex);
    }
    return this;

  }

  public PatientSearchCriteria withIdentificationFilter(final String identificationFilter) {
    if (this.filter == null) {
      this.filter = new Filter(null, null, null, null, null, null, null, identificationFilter);
    } else {
      this.filter = this.filter.withIdentification(identificationFilter);
    }
    return this;

  }

  public PatientSearchCriteria withMorbidity(final String identifier) {
    this.morbidity = identifier;
    return this;
  }

  public Optional<String> maybeMorbidity() {
    return Optional.ofNullable(morbidity);
  }

  public PatientSearchCriteria withDocument(final String identifier) {
    this.document = identifier;
    return this;
  }

  public Optional<String> maybeDocument() {
    return Optional.ofNullable(document);
  }


  public PatientSearchCriteria withStateCase(final String identifier) {
    this.stateCase = identifier;
    return this;
  }

  public Optional<String> maybeStateCase() {
    return Optional.ofNullable(stateCase);
  }


  public PatientSearchCriteria withAbcCase(final String identifier) {
    this.abcCase = identifier;
    return this;
  }

  public Optional<String> maybeAbcCase() {
    return Optional.ofNullable(abcCase);
  }

  public PatientSearchCriteria withCityCountyCase(final String identifier) {
    this.cityCountyCase = identifier;
    return this;
  }

  public Optional<String> maybeCityCountyCase() {
    return Optional.ofNullable(cityCountyCase);
  }

  public PatientSearchCriteria withNotification(final String identifier) {
    this.notification = identifier;
    return this;
  }

  public Optional<String> maybeNotification() {
    return Optional.ofNullable(notification);
  }

  public PatientSearchCriteria withTreatment(final String identifier) {
    this.treatment = identifier;
    return this;
  }

  public Optional<String> maybeTreatment() {
    return Optional.ofNullable(treatment);
  }

  public PatientSearchCriteria withVaccination(final String identifier) {
    this.vaccination = identifier;
    return this;
  }

  public Optional<String> maybeVaccination() {
    return Optional.ofNullable(vaccination);
  }

  public PatientSearchCriteria withInvestigation(final String identifier) {
    this.investigation = identifier;
    return this;
  }

  public Optional<String> maybeInvestigation() {
    return Optional.ofNullable(investigation);
  }

  public PatientSearchCriteria withLabReport(final String identifier) {
    this.labReport = identifier;
    return this;
  }

  public Optional<String> maybeLabReport() {
    return Optional.ofNullable(labReport);
  }

  public PatientSearchCriteria withAccessionNumber(final String identifier) {
    this.accessionNumber = identifier;
    return this;
  }

  public Optional<String> maybeAccessionNumber() {
    return Optional.ofNullable(accessionNumber);
  }

  public PatientSearchCriteria withBornOnDay(final int day) {
    if (this.bornOn != null) {
      this.bornOn = this.bornOn.withEquals(this.bornOn.equals().withDay(day));
    } else {
      this.bornOn = DateCriteria.equals(day, null, null);
    }
    return this;
  }

  public PatientSearchCriteria withBornOnMonth(final int month) {
    if (this.bornOn != null) {
      this.bornOn = this.bornOn.withEquals(this.bornOn.equals().withMonth(month));
    } else {
      this.bornOn = DateCriteria.equals(null, month, null);
    }
    return this;
  }

  public PatientSearchCriteria withBornOnYear(final int year) {
    if (this.bornOn != null) {
      this.bornOn = this.bornOn.withEquals(this.bornOn.equals().withYear(year));
    } else {
      this.bornOn = DateCriteria.equals(null, null, year);
    }
    return this;
  }

  public PatientSearchCriteria withBornBetween(final LocalDate from, final LocalDate to) {
    this.bornOn = DateCriteria.between(from, to);
    return this;
  }

  public Optional<NameCriteria> maybeName() {
    return Optional.ofNullable(this.name);
  }

  public PatientSearchCriteria withLastName(final TextCriteria criteria) {
    if (this.name == null) {

      this.name = new NameCriteria(null, criteria);
    } else {
      this.name = this.name.withLast(criteria);
    }
    return this;
  }

  public PatientSearchCriteria withFirstName(final TextCriteria criteria) {
    if (this.name == null) {
      this.name = new NameCriteria(criteria, null);
    } else {
      this.name = this.name.withFirst(criteria);
    }
    return this;
  }

  public Optional<LocationCriteria> maybeLocation() {
    return Optional.ofNullable(this.location);
  }

  public PatientSearchCriteria withStreet(final TextCriteria criteria) {
    if (this.location == null) {
      this.location = new LocationCriteria(criteria, null);
    } else {
      this.location = this.location.withStreet(criteria);
    }
    return this;
  }

  public PatientSearchCriteria withCity(final TextCriteria criteria) {
    if (this.location == null) {
      this.location = new LocationCriteria(null, criteria);
    } else {
      this.location = this.location.withCity(criteria);
    }
    return this;
  }

}
