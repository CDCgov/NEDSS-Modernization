package gov.cdc.nbs.patient.profile.create;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import gov.cdc.nbs.accumulation.Including;
import gov.cdc.nbs.patient.profile.address.AddressDemographic;
import gov.cdc.nbs.patient.profile.administrative.Administrative;
import gov.cdc.nbs.patient.profile.birth.BirthDemographic;
import gov.cdc.nbs.patient.profile.ethnicity.EthnicityDemographic;
import gov.cdc.nbs.patient.profile.gender.GenderDemographic;
import gov.cdc.nbs.patient.profile.names.NameDemographic;
import gov.cdc.nbs.time.json.FormattedLocalDateJsonDeserializer;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public record NewPatient(
    Administrative administrative,
    BirthDemographic birth,
    GenderDemographic gender,
    List<NameDemographic> names,
    List<AddressDemographic> addresses,
    List<Phone> phoneEmails,
    List<Race> races,
    List<Identification> identifications,
    EthnicityDemographic ethnicity) {

  public record Phone(
      @JsonDeserialize(using = FormattedLocalDateJsonDeserializer.class) LocalDate asOf,
      String type,
      String use,
      String countryCode,
      String phoneNumber,
      String extension,
      String email,
      String url,
      String comment) {
  }


  public record Race(
      @JsonDeserialize(using = FormattedLocalDateJsonDeserializer.class) LocalDate asOf,
      String race,
      List<String> detailed) {
  }


  public record Identification(
      @JsonDeserialize(using = FormattedLocalDateJsonDeserializer.class) LocalDate asOf,
      String type,
      String issuer,
      String id) {
  }

  public NewPatient() {
    this(
        null,
        null,
        null,
        Collections.emptyList(),
        Collections.emptyList(),
        Collections.emptyList(),
        Collections.emptyList(),
        Collections.emptyList(),
        null);
  }

  public NewPatient withAdministrative(final Administrative administrative) {
    return new NewPatient(
        administrative,
        birth(),
        gender(),
        names(),
        addresses(),
        phoneEmails(),
        races(),
        identifications(),
        ethnicity());
  }

  public NewPatient withName(final NameDemographic name) {
    return new NewPatient(
        administrative(),
        birth(),
        gender(),
        Including.include(names(), name),
        addresses(),
        phoneEmails(),
        races(),
        identifications(),
        ethnicity());
  }

  public NewPatient withAddress(final AddressDemographic value) {
    return new NewPatient(
        administrative(),
        birth(),
        gender(),
        names(),
        Including.include(addresses(), value),
        phoneEmails(),
        races(),
        identifications(),
        ethnicity());
  }

  public NewPatient withPhoneEmail(final Phone value) {
    return new NewPatient(
        administrative(),
        birth(),
        gender(),
        names(),
        addresses(),
        Including.include(phoneEmails(), value),
        races(),
        identifications(),
        ethnicity());
  }

  public NewPatient withRace(final Race value) {
    return new NewPatient(
        administrative(),
        birth(),
        gender(),
        names(),
        addresses(),
        phoneEmails(),
        Including.include(races(), value),
        identifications(),
        ethnicity());
  }

  public NewPatient withIdentification(final Identification value) {
    return new NewPatient(
        administrative(),
        birth(),
        gender(),
        names(),
        addresses(),
        phoneEmails(),
        races(),
        Including.include(identifications(), value),
        ethnicity());
  }

  public NewPatient withBirth(final BirthDemographic value) {
    return new NewPatient(
        administrative(),
        value,
        gender(),
        names(),
        addresses(),
        phoneEmails(),
        races(),
        identifications(),
        ethnicity());
  }

  public NewPatient withEthnicity(final EthnicityDemographic ethnicity) {
    return new NewPatient(
        administrative(),
        birth(),
        gender(),
        names(),
        addresses(),
        phoneEmails(),
        races(),
        identifications(),
        ethnicity);
  }

  public NewPatient withGender(final GenderDemographic value) {
    return new NewPatient(
        administrative(),
        birth(),
        value,
        names(),
        addresses(),
        phoneEmails(),
        races(),
        identifications(),
        ethnicity());
  }

  public Optional<Administrative> maybeAdministrative() {
    return Optional.ofNullable(administrative());
  }

  public Optional<BirthDemographic> maybeBirth() {
    return Optional.ofNullable(birth());
  }

  public Optional<EthnicityDemographic> maybeEthnicity() {
    return Optional.ofNullable(ethnicity());
  }

  public Optional<GenderDemographic> maybeGender() {
    return Optional.ofNullable(gender());
  }


}
