package gov.cdc.nbs.patient.demographics.race;

import gov.cdc.nbs.testing.support.Available;
import io.cucumber.java.en.Given;
import java.time.LocalDate;
import java.util.Objects;

public class RaceDemographicEntrySteps {

  private final Available<RaceDemographic> available;

  RaceDemographicEntrySteps(final Available<RaceDemographic> available) {
    this.available = available;
  }

  @Given("I select the entered race that is as of {localDate}")
  public void select(final LocalDate asOf) {
    this.available.select(item -> Objects.equals(item.asOf(), asOf));
  }

  @Given("I am entering the {raceCategory} race as of {localDate}")
  public void entering(final String race, final LocalDate asOf) {
    this.available.selected(new RaceDemographic(asOf, race));
  }

  @Given("I enter the race as of {localDate}")
  public void asOf(final LocalDate value) {
    this.available.selected(current -> current.withAsOf(value));
  }

  @Given("I enter the race category {raceCategory}")
  public void type(final String value) {
    this.available.selected(current -> current.withRace(value));
  }

  @Given("I am entering the detailed race {raceDetail}")
  public void entering(final String detail) {
    this.available.selected(current -> current.withDetail(detail));
  }

  @Given("I remove the entered race as of {localDate}")
  public void remove(final LocalDate asOf) {
    this.available.removeIf(item -> Objects.equals(item.asOf(), asOf));
  }
}
