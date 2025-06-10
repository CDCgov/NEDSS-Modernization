package gov.cdc.nbs.patient.profile.create;

import gov.cdc.nbs.accumulation.Including;
import gov.cdc.nbs.patient.demographics.address.AddressDemographic;
import gov.cdc.nbs.patient.demographics.administrative.Administrative;
import gov.cdc.nbs.patient.profile.birth.BirthDemographic;
import gov.cdc.nbs.patient.profile.ethnicity.EthnicityDemographic;
import gov.cdc.nbs.patient.profile.gender.GenderDemographic;
import gov.cdc.nbs.patient.profile.general.GeneralInformationDemographic;
import gov.cdc.nbs.patient.demographics.identification.IdentificationDemographic;
import gov.cdc.nbs.patient.profile.mortality.MortalityDemographic;
import gov.cdc.nbs.patient.demographics.name.NameDemographic;
import gov.cdc.nbs.patient.demographics.phone.PhoneDemographic;
import gov.cdc.nbs.patient.profile.race.RaceDemographic;

import java.util.List;
import java.util.Optional;

public record NewPatient(
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
    List<IdentificationDemographic> identifications
) {

  public NewPatient(
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
      List<IdentificationDemographic> identifications
  ) {
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

  public NewPatient() {
    this(
        null,
        null,
        null,
        null,
        null,
        null,
        null,
        null,
        null,
        null,
        null
    );
  }

  public NewPatient withAdministrative(final Administrative administrative) {
    return new NewPatient(
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
        identifications()
    );
  }

  public NewPatient withName(final NameDemographic name) {
    return new NewPatient(
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
        identifications()
    );
  }

  public NewPatient withAddress(final AddressDemographic value) {
    return new NewPatient(
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
        identifications()
    );
  }

  public NewPatient withPhoneEmail(final PhoneDemographic value) {
    return new NewPatient(
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

  public NewPatient withRace(final RaceDemographic value) {
    return new NewPatient(
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
        identifications()
    );
  }

  public NewPatient withIdentification(final IdentificationDemographic value) {
    return new NewPatient(
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
        Including.include(identifications(), value)
    );
  }

  public NewPatient withBirth(final BirthDemographic value) {
    return new NewPatient(
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
        identifications()
    );
  }

  public NewPatient withEthnicity(final EthnicityDemographic value) {
    return new NewPatient(
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
        identifications()
    );
  }

  public NewPatient withGender(final GenderDemographic value) {
    return new NewPatient(
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
        identifications()
    );
  }

  public NewPatient withMortality(final MortalityDemographic value) {
    return new NewPatient(
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
        identifications()
    );
  }

  public NewPatient withGeneralInformation(final GeneralInformationDemographic value) {
    return new NewPatient(
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
        identifications()
    );
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
