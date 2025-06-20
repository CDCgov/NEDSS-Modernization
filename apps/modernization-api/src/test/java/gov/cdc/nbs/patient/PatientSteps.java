package gov.cdc.nbs.patient;

import gov.cdc.nbs.patient.identifier.PatientIdentifier;
import gov.cdc.nbs.testing.support.Active;
import io.cucumber.java.en.Given;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Transactional
public class PatientSteps {


  private final Active<PatientIdentifier> patient;
  private final PatientMother mother;

  PatientSteps(
      final Active<PatientIdentifier> patient,
      final PatientMother mother
  ) {
    this.patient = patient;
    this.mother = mother;
  }

  @Given("I have a(nother) patient")
  public void i_have_a_patient() {
    mother.create();
  }

  @Given("the patient has been deleted")
  @Given("the patient is inactive")
  public void the_patient_is_inactive() {
    mother.deleted(patient.active());
  }

  @Given("the patient is superseded")
  public void the_patient_is_superseded() {
    mother.superseded(patient.active());
  }

  @Given("the patient has a(n) {string} of {string}")
  public void the_patient_has_a_field_with_a_value_of(
      final String field,
      final String value) {

    PatientIdentifier identifier = this.patient.active();

    switch (field.toLowerCase()) {
      case "first name" -> mother.withName(
          identifier,
          "L",
          value,
          null);

      case "last name" -> mother.withName(
          identifier,
          "L",
          null,
          value);

      case "race" -> mother.withRace(
          identifier,
          resolveRace(value));

      case "id" -> mother.withId(identifier, Long.parseLong(value));

      case "local id" -> mother.withLocalId(identifier, value);

      case "phone number" -> mother.withPhone(
          identifier,
          value);

      case "country code" -> mother.withPhone(
          identifier,
          value,
          null,
          null);

      case "extension" -> mother.withPhone(
          identifier,
          null,
          null,
          value);

      case "email", "email address" -> mother.withEmail(
          identifier,
          value);

      case "address" -> mother.withAddress(
          identifier,
          value,
          null,
          null,
          null,
          null);

      case "zip" -> mother.withAddress(
          identifier,
          null,
          null,
          null,
          null,
          value);

      case "city" -> mother.withAddress(
          identifier,
          null,
          value,
          null,
          null,
          null);

      case "county" -> mother.withAddress(
          identifier,
          null,
          null,
          value,
          null,
          null);

      case "country" -> mother.withAddress(
          identifier,
          null,
          null,
          null,
          null,
          null);

      case "state" -> mother.withAddress(
          identifier,
          null,
          null,
          null,
          value,
          null);

      default -> throw new IllegalStateException("Unexpected patient demographic data: " + field + ":" + value);
    }

  }

  private String resolveRace(final String value) {
    return switch (value.toLowerCase()) {
      case "american indian or alaska native", "american indian", "alaska native" -> "1002-5";
      case "asian" -> "2028-9";
      case "black or african american", "black", "african american" -> "2054-5";
      case "native hawaiian or other pacific islander", "native hawaiian", "pacific islander" -> "2076-8";
      case "white" -> "2106-3";
      case "other race", "other" -> "2131-1";
      case "not asked" -> "NASK";
      case "refused to answer", "refused" -> "PHC1175";
      case "multi-race", "multi" -> "M";
      default -> "U";
    };
  }

  @Given("the patient has the phone number {string}-{string} x{string}")
  public void the_patient_has_the_phone_number(
      final String countryCode,
      final String number,
      final String extension) {

    PatientIdentifier identifier = this.patient.active();

    mother.withPhone(
        identifier,
        countryCode,
        number,
        extension);

  }

  @Given("the patient is associated with state HIV case {string}")
  public void the_patient_is_associated_with_state_HIV_case(final String value) {
    patient.maybeActive().ifPresent(current -> mother.withStateHIVCase(current, value));
  }
}
