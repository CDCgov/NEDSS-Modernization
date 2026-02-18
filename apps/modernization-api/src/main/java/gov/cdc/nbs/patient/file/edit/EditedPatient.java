package gov.cdc.nbs.patient.file.edit;

import gov.cdc.nbs.accumulation.Including;
import gov.cdc.nbs.patient.demographics.address.AddressDemographic;
import gov.cdc.nbs.patient.demographics.administrative.Administrative;
import gov.cdc.nbs.patient.demographics.birth.BirthDemographic;
import gov.cdc.nbs.patient.demographics.ethnicity.EthnicityDemographic;
import gov.cdc.nbs.patient.demographics.gender.GenderDemographic;
import gov.cdc.nbs.patient.demographics.general.GeneralInformationDemographic;
import gov.cdc.nbs.patient.demographics.identification.IdentificationDemographic;
import gov.cdc.nbs.patient.demographics.mortality.MortalityDemographic;
import gov.cdc.nbs.patient.demographics.name.NameDemographic;
import gov.cdc.nbs.patient.demographics.phone.PhoneDemographic;
import gov.cdc.nbs.patient.demographics.race.RaceDemographic;
import java.util.List;
import java.util.Optional;

public record EditedPatient(
    Administrative administrative,
    BirthDemographic birth,
    GenderDemographic gender,
    EthnicityDemographic ethnicity,
    MortalityDemographic mortality,
    GeneralInformationDemographic general,
    List<NameDemographic> names,
    List<AddressDemographic> addresses,
    List<PhoneDemographic> phoneEmails,
    List<RaceDemographic> races,
    List<IdentificationDemographic> identifications) {

  public EditedPatient(
      Administrative administrative,
      BirthDemographic birth,
      GenderDemographic gender,
      EthnicityDemographic ethnicity,
      MortalityDemographic mortality,
      GeneralInformationDemographic general,
      List<NameDemographic> names,
      List<AddressDemographic> addresses,
      List<PhoneDemographic> phoneEmails,
      List<RaceDemographic> races,
      List<IdentificationDemographic> identifications) {
    this.administrative = administrative;
    this.birth = birth;
    this.gender = gender;
    this.ethnicity = ethnicity;
    this.mortality = mortality;
    this.general = general;
    this.names = names == null ? List.of() : List.copyOf(names);
    this.addresses = addresses == null ? List.of() : List.copyOf(addresses);
    this.phoneEmails = phoneEmails == null ? List.of() : List.copyOf(phoneEmails);
    this.races = races == null ? List.of() : List.copyOf(races);
    this.identifications = identifications == null ? List.of() : List.copyOf(identifications);
  }

  public EditedPatient() {
    this(null, null, null, null, null, null, null, null, null, null, null);
  }

  public EditedPatient withAdministrative(final Administrative administrative) {
    return new EditedPatient(
        administrative,
        birth(),
        gender(),
        ethnicity(),
        mortality(),
        general(),
        names(),
        addresses(),
        phoneEmails(),
        races(),
        identifications());
  }

  public EditedPatient withName(final NameDemographic name) {
    return new EditedPatient(
        administrative(),
        birth(),
        gender(),
        ethnicity(),
        mortality(),
        general(),
        Including.include(names(), name),
        addresses(),
        phoneEmails(),
        races(),
        identifications());
  }

  public EditedPatient withAddress(final AddressDemographic value) {
    return new EditedPatient(
        administrative(),
        birth(),
        gender(),
        ethnicity(),
        mortality(),
        general(),
        names(),
        Including.include(addresses(), value),
        phoneEmails(),
        races(),
        identifications());
  }

  public EditedPatient withPhoneEmail(final PhoneDemographic value) {
    return new EditedPatient(
        administrative(),
        birth(),
        gender(),
        ethnicity(),
        mortality(),
        general(),
        names(),
        addresses(),
        Including.include(phoneEmails(), value),
        races(),
        identifications());
  }

  public EditedPatient withRace(final RaceDemographic value) {
    return new EditedPatient(
        administrative(),
        birth(),
        gender(),
        ethnicity(),
        mortality(),
        general(),
        names(),
        addresses(),
        phoneEmails(),
        Including.include(races(), value),
        identifications());
  }

  public EditedPatient withIdentification(final IdentificationDemographic value) {
    return new EditedPatient(
        administrative(),
        birth(),
        gender(),
        ethnicity(),
        mortality(),
        general(),
        names(),
        addresses(),
        phoneEmails(),
        races(),
        Including.include(identifications(), value));
  }

  public EditedPatient withBirth(final BirthDemographic value) {
    return new EditedPatient(
        administrative(),
        value,
        gender(),
        ethnicity(),
        mortality(),
        general(),
        names(),
        addresses(),
        phoneEmails(),
        races(),
        identifications());
  }

  public EditedPatient withEthnicity(final EthnicityDemographic value) {
    return new EditedPatient(
        administrative(),
        birth(),
        gender(),
        value,
        mortality(),
        general(),
        names(),
        addresses(),
        phoneEmails(),
        races(),
        identifications());
  }

  public EditedPatient withGender(final GenderDemographic value) {
    return new EditedPatient(
        administrative(),
        birth(),
        value,
        ethnicity(),
        mortality(),
        general(),
        names(),
        addresses(),
        phoneEmails(),
        races(),
        identifications());
  }

  public EditedPatient withMortality(final MortalityDemographic value) {
    return new EditedPatient(
        administrative(),
        birth(),
        gender(),
        ethnicity(),
        value,
        general(),
        names(),
        addresses(),
        phoneEmails(),
        races(),
        identifications());
  }

  public EditedPatient withGeneralInformation(final GeneralInformationDemographic value) {
    return new EditedPatient(
        administrative(),
        birth(),
        gender(),
        ethnicity(),
        mortality(),
        value,
        names(),
        addresses(),
        phoneEmails(),
        races(),
        identifications());
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

  public Optional<MortalityDemographic> maybeMortality() {
    return Optional.ofNullable(mortality());
  }

  public Optional<GeneralInformationDemographic> maybeGeneralInformation() {
    return Optional.ofNullable(general());
  }
}
