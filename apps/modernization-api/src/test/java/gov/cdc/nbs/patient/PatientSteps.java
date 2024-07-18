package gov.cdc.nbs.patient;

import gov.cdc.nbs.patient.identifier.PatientIdentifier;
import gov.cdc.nbs.testing.support.Active;
import io.cucumber.java.Before;
import io.cucumber.java.ParameterType;
import io.cucumber.java.en.Given;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Transactional
public class PatientSteps {


  private final Active<PatientIdentifier> patient;
  private final PatientMother mother;

  PatientSteps(
      final Active<PatientIdentifier> patient,
      final PatientMother mother) {
    this.patient = patient;
    this.mother = mother;
  }

  //  Make sure that patients are cleaned up after everything else
  @Before(order = 15000)
  public void clean() {
    mother.reset();
  }

  @Given("I have a(nother) patient")
  public void i_have_a_patient() {
    mother.create();
  }

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

      case "birthday" -> mother.withBirthday(
          identifier,
          LocalDate.parse(value));

      case "sex" -> mother.withGender(
          identifier,
          value);

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
          null);

      case "zip" -> mother.withAddress(
          identifier,
          null,
          null,
          null,
          value);

      case "city" -> mother.withAddress(
          identifier,
          null,
          value,
          null,
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

  @Given("the patient has the gender {gender}")
  public void the_patient_has_the_gender(final String gender) {
    this.patient.maybeActive().ifPresent(found -> mother.withGender(found, gender));
  }

  @ParameterType(name = "gender", value = "(?i)(male|female|unknown)")
  public String gender(final String value) {
    return switch (value.toLowerCase()) {
      case "male" -> "M";
      case "female" -> "F";
      case "unknown" -> "U";
      default -> value;
    };
  }

  @Given("the patient has a race")
  public void the_patient_has_a_race() {
    patient.maybeActive().ifPresent(mother::withRace);
  }

  @Given("the patient has the race category {raceCategory}")
  public void the_patient_has_the_race_category(final String category) {
    patient.maybeActive().ifPresent(current -> mother.withRace(current, category));
  }

  @Given("the patient race of {raceCategory} includes {raceDetail}")
  public void the_patient_race_of_category_includes(final String category, final String detail) {
    patient.maybeActive().ifPresent(current -> mother.withRaceIncluding(current, category, detail));
  }

  @Given("$the patient has the ethnicity {ethnicity}^")
  public void the_patient_has_the_ethnicity(final String ethnicity) {
    patient.maybeActive().ifPresent(current -> mother.withEthnicity(current, ethnicity));
  }

  @Given("the patient has the ethnicity {ethnicity}, specifically {ethnicityDetail}")
  public void the_patient_has_the_ethnicity_specifically(final String ethnicity, final String detail) {
    patient.maybeActive().ifPresent(current -> mother.withSpecificEthnicity(current, ethnicity, detail));
  }

  @Given("the patient is associated with state HIV case {string}")
  public void the_patient_is_associated_with_state_HIV_case(final String value) {
    patient.maybeActive().ifPresent(current -> mother.withStateHIVCase(current, value));
  }
}
