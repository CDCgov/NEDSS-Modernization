package gov.cdc.nbs.patient.profile.birth;

import gov.cdc.nbs.testing.support.Active;
import io.cucumber.java.en.Given;

import java.time.LocalDate;

public class BirthDemographicEntrySteps {

  private final Active<BirthDemographic> input;

  BirthDemographicEntrySteps(final Active<BirthDemographic> input) {
    this.input = input;
  }

  @Given("I enter the birth demographics as of date {localDate}")
  public void i_enter_patient_birth_as_of(final LocalDate value) {
    this.input.active(new BirthDemographic(value));
  }

  @Given("I enter the birth demographics with the patient born on {localDate}")
  public void i_enter_patient_birth_born_on(final LocalDate value) {
    this.input.active(current -> current.withBornOn(value));
  }

  @Given("I enter the birth demographics with the patient born as {sex}")
  public void i_enter_patient_birth_born_as(final String value) {
    this.input.active(current -> current.withSex(value));
  }

  @Given("I enter the birth demographics with the patient multiple as {indicator}")
  public void i_enter_patient_birth_multiple_as(final String value) {
    this.input.active(current -> current.withMultiple(value));
  }

  @Given("I enter the birth demographics with the patient born {nth}")
  public void i_enter_patient_birth_multiple_as(final int value) {
    this.input.active(current -> current.withOrder(value));
  }

  @Given("I enter the birth demographics with the patient born in the city of {string}")
  public void i_enter_patient_birth_city_as(final String value) {
    this.input.active(current -> current.withCity(value));
  }

  @Given("I enter the birth demographics with the patient born in the county of {county}")
  public void i_enter_patient_birth_county_as(final String value) {
    this.input.active(current -> current.withCounty(value));
  }

  @Given("I enter the birth demographics with the patient born in the state of {state}")
  public void i_enter_patient_birth_state_as(final String value) {
    this.input.active(current -> current.withState(value));
  }

  @Given("I enter the birth demographics with the patient born in the country of {country}")
  public void i_enter_patient_birth_country_as(final String value) {
    this.input.active(current -> current.withCountry(value));
  }
}
