package gov.cdc.nbs.patient.demographics.name;

import java.time.Clock;
import java.time.LocalDate;
import java.util.Objects;
import java.util.function.Supplier;

import gov.cdc.nbs.testing.support.Available;
import io.cucumber.java.en.Given;

public class NameDemographicEntrySteps {

  private final Available<NameDemographic> availableNames;
  private final Clock clock;

  NameDemographicEntrySteps(
      final Available<NameDemographic> availableNames,
      final Clock clock) {
    this.availableNames = availableNames;
    this.clock = clock;
  }

  private Supplier<NameDemographic> initial() {
    return () -> new NameDemographic(LocalDate.now(clock), "legal");
  }

  private Supplier<NameDemographic> initial(final LocalDate asOf, String use) {
    return () -> new NameDemographic(asOf, use);
  }

  @Given("I am entering a {nameUse} name as of {localDate}")
  public void i_am_entering_a_name_as_of(final String use, final LocalDate asOf) {
    this.availableNames.selected(current -> current.withAsOf(asOf).withType(use), initial(asOf, use));
  }

  @Given("I enter the prefix {namePrefix} on the current name")
  public void i_add_the_prefix(final String value) {
    this.availableNames.selected(current -> current.withPrefix(value), initial());
  }

  @Given("I enter the first name {string} on the current name")
  public void i_add_the_first_name(final String value) {
    this.availableNames.selected(current -> current.withFirst(value), initial());
  }

  @Given("I enter the middle name {string} on the current name")
  public void i_add_the_middle_name(final String value) {
    this.availableNames.selected(current -> current.withMiddle(value), initial());
  }

  @Given("I enter the second middle name {string} on the current name")
  public void i_add_the_second_middle_name(final String value) {
    this.availableNames.selected(current -> current.withSecondMiddle(value), initial());
  }

  @Given("I enter the last name {string} on the current name")
  public void i_add_the_last_name(final String value) {
    this.availableNames.selected(current -> current.withLast(value), initial());
  }

  @Given("I enter the second last name {string} on the current name")
  public void i_add_the_second_last_name(final String value) {
    this.availableNames.selected(current -> current.withSecondLast(value), initial());
  }

  @Given("I enter the suffix {nameSuffix} on the current name")
  public void i_add_the_suffix(final String value) {
    this.availableNames.selected(current -> current.withSuffix(value), initial());
  }

  @Given("I enter the degree {degree} on the current name")
  public void i_add_the_degree(final String value) {
    this.availableNames.selected(current -> current.withDegree(value), initial());
  }

  @Given("I select the entered name that is as of {localDate}")
  public void select(final LocalDate asOf) {
    this.availableNames.select(item -> Objects.equals(item.asOf(), asOf));
  }

  @Given("I remove the entered name as of {localDate}")
  public void remove(final LocalDate asOf) {
    this.availableNames.removeIf(item -> Objects.equals(item.asOf(), asOf));
  }

}
