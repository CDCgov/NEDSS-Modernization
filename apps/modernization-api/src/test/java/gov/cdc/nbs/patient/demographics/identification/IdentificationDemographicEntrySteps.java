package gov.cdc.nbs.patient.demographics.identification;

import gov.cdc.nbs.testing.support.Active;
import io.cucumber.java.en.Given;

import java.time.LocalDate;

public class IdentificationDemographicEntrySteps {

  private final Active<IdentificationDemographic> active;

  IdentificationDemographicEntrySteps(final Active<IdentificationDemographic> active) {
    this.active = active;
  }

  @Given("I am entering a(n) {identificationType} identification of {string} as of {localDate}")
  public void entering(
      final String type,
      final String value,
      final LocalDate asOf
  ) {
    this.active.active(new IdentificationDemographic(asOf, type, null, value));
  }
}
