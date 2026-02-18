package gov.cdc.nbs.patient.demographics.identification;

import gov.cdc.nbs.testing.support.Available;
import io.cucumber.java.en.Given;
import java.time.LocalDate;
import java.util.Objects;

public class IdentificationDemographicEntrySteps {

  private final Available<IdentificationDemographic> available;

  IdentificationDemographicEntrySteps(final Available<IdentificationDemographic> available) {
    this.available = available;
  }

  @Given("I am entering a(n) {identificationType} identification of {string} as of {localDate}")
  public void entering(final String type, final String value, final LocalDate asOf) {
    this.available.selected(
        current -> current.withAsOf(asOf).withType(type).withValue(value),
        () -> new IdentificationDemographic(asOf, type, null, value, null));
  }

  @Given("the entered identification was issued by {assigningAuthority}")
  public void authorizedBy(final String authority) {
    this.available.selected(
        current -> current.withIssuer(authority),
        () -> new IdentificationDemographic(null, null, authority, null, null));
  }

  @Given("I select the entered identification that is as of {localDate}")
  public void select(final LocalDate asOf) {
    this.available.select(item -> Objects.equals(item.asOf(), asOf));
  }

  @Given("I remove the entered identification with as of {localDate}")
  public void remove(final LocalDate asOf) {
    this.available.removeIf(item -> Objects.equals(item.asOf(), asOf));
  }
}
