package gov.cdc.nbs.patient.demographics.birth;

import gov.cdc.nbs.testing.support.Active;
import io.cucumber.java.en.Given;
import java.time.Clock;
import java.time.LocalDate;
import java.util.function.Supplier;

public class BirthDemographicEntrySteps {

  private final Clock clock;
  private final Active<BirthDemographic> input;

  BirthDemographicEntrySteps(final Clock clock, final Active<BirthDemographic> input) {
    this.clock = clock;
    this.input = input;
  }

  private Supplier<BirthDemographic> initial() {
    return () -> new BirthDemographic(LocalDate.now(clock));
  }

  @Given("I enter the birth demographics as of date {localDate}")
  public void i_enter_patient_birth_as_of(final LocalDate value) {
    this.input.active(current -> current.withAsOf(value), initial());
  }

  @Given("I enter the birth demographics with the patient born on {localDate}")
  public void i_enter_patient_birth_born_on(final LocalDate value) {
    this.input.active(current -> current.withBornOn(value), initial());
  }

  @Given("I enter the birth demographics with the patient born as {sex}")
  public void i_enter_patient_birth_born_as(final String value) {
    this.input.active(current -> current.withSex(value), initial());
  }

  @Given("I enter the birth demographics with the patient multiple as {indicator}")
  public void i_enter_patient_birth_multiple_as(final String value) {
    this.input.active(current -> current.withMultiple(value), initial());
  }

  @Given("I enter the birth demographics with the patient born {nth}")
  public void i_enter_patient_birth_multiple_as(final int value) {
    this.input.active(current -> current.withOrder(value), initial());
  }

  @Given("I enter the birth demographics with the patient born in the city of {string}")
  public void i_enter_patient_birth_city_as(final String value) {
    this.input.active(current -> current.withCity(value), initial());
  }

  @Given("I enter the birth demographics with the patient born in the county of {county}")
  public void i_enter_patient_birth_county_as(final String value) {
    this.input.active(current -> current.withCounty(value), initial());
  }

  @Given("I enter the birth demographics with the patient born in the state of {state}")
  public void i_enter_patient_birth_state_as(final String value) {
    this.input.active(current -> current.withState(value), initial());
  }

  @Given("I enter the birth demographics with the patient born in the country of {country}")
  public void i_enter_patient_birth_country_as(final String value) {
    this.input.active(current -> current.withCountry(value), initial());
  }
}
