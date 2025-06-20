package gov.cdc.nbs.patient.demographics.mortality;

import gov.cdc.nbs.testing.support.Active;
import io.cucumber.java.en.Given;

import java.time.LocalDate;

public class MortalityDemographicEntrySteps {

  private final Active<MortalityDemographic> input;

  MortalityDemographicEntrySteps(final Active<MortalityDemographic> input) {
    this.input = input;
  }

  @Given("I am entering the mortality as of date {localDate}")
  public void i_am_entering_patient_mortality_as_of(final LocalDate asOf) {
    this.input.active(new MortalityDemographic(asOf, null, null, null, null, null, null));
  }

  @Given("I enter the mortality city {string}")
  public void i_enter_the_patient_mortality_city(final String city) {
    this.input.active(current -> current.withCity(city));
  }

  @Given("I enter the mortality country {country}")
  public void i_enter_the_patient_mortality_country(final String country) {
    this.input.active(current -> current.withCountry(country));
  }

  @Given("I enter the mortality county {county}")
  public void i_enter_the_patient_mortality_county(final String county) {
    this.input.active(current -> current.withCounty(county));
  }

  @Given("I enter the mortality state {state}")
  public void i_enter_the_patient_mortality_state(final String state) {
    this.input.active(current -> current.withState(state));
  }

  @Given("I enter the mortality deceased on date of {localDate}")
  public void i_am_entering_patient_mortality_deceased_on_date_of(final LocalDate deceasedOn) {
    this.input.active(current -> current.withDeceasedOn(deceasedOn));
  }

  @Given("I enter the mortality deceased option as {indicator}")
  public void i_enter_the_patient_mortality_deceased_option_as(final String indicator) {
    this.input.active(current -> current.withDeceased(indicator));
  }
}
