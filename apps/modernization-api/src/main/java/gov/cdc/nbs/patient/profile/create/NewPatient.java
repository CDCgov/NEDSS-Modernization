package gov.cdc.nbs.patient.profile.create;

import gov.cdc.nbs.accumulation.Including;
import gov.cdc.nbs.patient.profile.administrative.Administrative;
import gov.cdc.nbs.patient.profile.birth.BirthDemographic;
import gov.cdc.nbs.patient.profile.gender.GenderDemographic;
import gov.cdc.nbs.patient.profile.names.NameDemographic;

import java.time.Instant;
import java.util.Collections;
import java.util.List;

public record NewPatient(
    Administrative administrative,
    BirthDemographic birth,
    GenderDemographic gender,
    List<NameDemographic> names,
    List<Address> addresses,
    List<Phone> phoneEmails,
    List<Race> races,
    List<Identification> identifications
) {

  public record Address(
      Instant asOf,
      String type,
      String use,
      String address1,
      String address2,
      String city,
      String state,
      String zipcode,
      String county,
      String censusTract,
      String country,
      String comment) {
  }


  public record Phone(
      Instant asOf,
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
      Instant asOf,
      String race,
      List<String> detailed) {
  }


  public record Identification(
      Instant asOf,
      String type,
      String issuer,
      String id) {
  }

  public NewPatient(Instant asOf) {
    this(
        new Administrative(asOf),
        null,
        null,
        Collections.emptyList(),
        Collections.emptyList(),
        Collections.emptyList(),
        Collections.emptyList(),
        Collections.emptyList());
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
        identifications()
    );
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
        identifications()
    );
  }

  public NewPatient withAddress(final Address value) {
    return new NewPatient(
        administrative(),
        birth(),
        gender(),
        names(),
        Including.include(addresses(), value),
        phoneEmails(),
        races(),
        identifications()
    );
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
        identifications()
    );
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
        identifications()
    );
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
        Including.include(identifications(), value)
    );
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
        identifications()
    );
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
        identifications()
    );
  }
}
