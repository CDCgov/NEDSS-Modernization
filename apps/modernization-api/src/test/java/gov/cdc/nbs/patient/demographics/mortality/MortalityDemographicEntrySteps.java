package gov.cdc.nbs.patient.demographics.mortality;

import gov.cdc.nbs.testing.support.Active;
import io.cucumber.java.en.Given;

import java.time.Clock;
import java.time.LocalDate;
import java.util.function.Supplier;

public class MortalityDemographicEntrySteps {

  private final Clock clock;
  private final Active<MortalityDemographic> input;

  MortalityDemographicEntrySteps(final Clock clock, final Active<MortalityDemographic> input) {
    this.clock = clock;
    this.input = input;
  }

  private Supplier<MortalityDemographic> initial() {
    return () -> new MortalityDemographic(LocalDate.now(clock));
  }

  @Given("I am entering the mortality as of date {localDate}")
  public void i_am_entering_patient_mortality_as_of(final LocalDate asOf) {
    this.input.active(current -> current.withAsOf(asOf), initial());
  }

  @Given("I enter the mortality city {string}")
  public void i_enter_the_patient_mortality_city(final String city) {
    this.input.active(current -> current.withCity(city), initial());
  }

  @Given("I enter the mortality country {country}")
  public void i_enter_the_patient_mortality_country(final String country) {
    this.input.active(current -> current.withCountry(country), initial());
  }

  @Given("I enter the mortality county {county}")
  public void i_enter_the_patient_mortality_county(final String county) {
    this.input.active(current -> current.withCounty(county), initial());
  }

  @Given("I enter the mortality state {state}")
  public void i_enter_the_patient_mortality_state(final String state) {
    this.input.active(current -> current.withState(state), initial());
  }

  @Given("I enter the mortality deceased on date of {localDate}")
  public void i_am_entering_patient_mortality_deceased_on_date_of(final LocalDate deceasedOn) {
    this.input.active(current -> current.withDeceasedOn(deceasedOn), initial());
  }

  @Given("I enter the mortality deceased option as {indicator}")
  public void i_enter_the_patient_mortality_deceased_option_as(final String indicator) {
    this.input.active(current -> current.withDeceased(indicator), initial());
  }
}
