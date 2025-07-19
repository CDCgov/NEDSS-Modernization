package gov.cdc.nbs.patient.demographics.address;

import gov.cdc.nbs.testing.support.Available;
import io.cucumber.java.en.Given;

import java.time.LocalDate;
import java.util.Objects;

public class AddressDemographicEntrySteps {

  private final Available<AddressDemographic> available;

  AddressDemographicEntrySteps(final Available<AddressDemographic> available) {
    this.available = available;
  }

  @Given("I am entering the {addressType} - {addressUse} address at {string} {string} {string} as of {localDate}")
  public void create(
      final String type,
      final String use,
      final String address,
      final String city,
      final String zip,
      final LocalDate asOf
  ) {
    this.available.available(
        new AddressDemographic(
            asOf,
            type,
            use,
            address,
            null,
            city,
            null,
            zip,
            null,
            null,
            null,
            null
        )
    );
  }

  @Given("I select the entered address that is as of {localDate}")
  public void select(final LocalDate asOf) {
    this.available.selectFirst(item -> Objects.equals(item.asOf(), asOf));
  }

  @Given("I remove the entered address as of {localDate}")
  public void remove(final LocalDate asOf) {
    this.available.removeIf(item -> Objects.equals(item.asOf(), asOf));
  }

  @Given("I enter the address as of {localDate}")
  public void asOf(final LocalDate value) {
    this.available.first(current -> current.withAsOf(value));
  }

  @Given("I enter the address type {addressType}")
  public void type(final String value) {
    this.available.first(current -> current.withType(value));

  }

  @Given("I enter the address use {addressUse}")
  public void use(final String value) {
    this.available.first(current -> current.withUse(value));

  }
}
