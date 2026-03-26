package gov.cdc.nbs.patient.demographics.address;

import gov.cdc.nbs.testing.support.Available;
import io.cucumber.java.en.Given;
import java.time.Clock;
import java.time.LocalDate;
import java.util.Objects;
import java.util.function.Supplier;

public class AddressDemographicEntrySteps {

  private final Clock clock;
  private final Available<AddressDemographic> available;

  AddressDemographicEntrySteps(final Clock clock, final Available<AddressDemographic> available) {
    this.clock = clock;
    this.available = available;
  }

  private Supplier<AddressDemographic> initial() {
    return () -> new AddressDemographic(LocalDate.now(clock));
  }

  @Given(
      "I am entering the {addressType} - {addressUse} address at {string} {string} {string} as of {localDate}")
  public void create(
      final String type,
      final String use,
      final String address,
      final String city,
      final String zip,
      final LocalDate asOf) {
    this.available.selected(
        current ->
            current
                .withAsOf(asOf)
                .withType(type)
                .withUse(use)
                .withAddress(address)
                .withCity(city)
                .withZipcode(zip),
        initial());
  }

  @Given("I enter the address as of {localDate}")
  public void asOf(final LocalDate value) {
    this.available.selected(current -> current.withAsOf(value), initial());
  }

  @Given("I enter the address type {addressType}")
  public void type(final String value) {
    this.available.selected(current -> current.withType(value), initial());
  }

  @Given("I enter the address use {addressUse}")
  public void use(final String value) {
    this.available.selected(current -> current.withUse(value), initial());
  }

  @Given("I enter the address street address {string}")
  public void address(final String value) {
    this.available.selected(current -> current.withAddress(value), initial());
  }

  @Given("I enter the address street address2 {string}")
  public void address2(final String value) {
    this.available.selected(current -> current.withAddress2(value), initial());
  }

  @Given("I enter the address city {string}")
  public void city(final String value) {
    this.available.selected(current -> current.withCity(value), initial());
  }

  @Given("I enter the address zipcode {string}")
  public void zipcode(final String value) {
    this.available.selected(current -> current.withZipcode(value), initial());
  }

  @Given("I enter the address state {state}")
  public void state(final String value) {
    this.available.selected(current -> current.withState(value), initial());
  }

  @Given("I enter the address county {county}")
  public void county(final String value) {
    this.available.selected(current -> current.withCounty(value), initial());
  }

  @Given("I enter the address country {country}")
  public void country(final String value) {
    this.available.selected(current -> current.withCountry(value), initial());
  }

  @Given("I enter the address census tract {string}")
  public void censusTract(final String value) {
    this.available.selected(current -> current.withCensusTract(value), initial());
  }

  @Given("I enter the address comment {string}")
  public void comment(final String value) {
    this.available.selected(current -> current.withComment(value), initial());
  }

  @Given("I select the entered address that is as of {localDate}")
  public void select(final LocalDate asOf) {
    this.available.select(item -> Objects.equals(item.asOf(), asOf));
  }

  @Given("I remove the entered address as of {localDate}")
  public void remove(final LocalDate asOf) {
    this.available.removeIf(item -> Objects.equals(item.asOf(), asOf));
  }
}
