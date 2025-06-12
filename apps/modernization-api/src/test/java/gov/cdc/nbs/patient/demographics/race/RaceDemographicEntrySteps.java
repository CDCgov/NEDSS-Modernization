package gov.cdc.nbs.patient.demographics.race;

import gov.cdc.nbs.testing.support.Active;
import io.cucumber.java.en.Given;

import java.time.LocalDate;

public class RaceDemographicEntrySteps {

  private final Active<RaceDemographic> active;

  RaceDemographicEntrySteps(final Active<RaceDemographic> active) {
    this.active = active;
  }

  @Given("I am entering the {raceCategory} race as of {localDate}")
  public void enteringPhone(
      final String race,
      final LocalDate asOf
  ) {
    this.active.active(new RaceDemographic(asOf, race));
  }

  @Given("I am entering the detailed race {raceDetail}")
  public void enteringEmail(final String detail) {
    this.active.active(current -> current.withDetail(detail));
  }
}
