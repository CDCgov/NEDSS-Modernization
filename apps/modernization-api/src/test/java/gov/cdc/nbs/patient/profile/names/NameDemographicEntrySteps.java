package gov.cdc.nbs.patient.profile.names;

import gov.cdc.nbs.testing.support.Active;
import io.cucumber.java.en.Given;

import java.time.LocalDate;

public class NameDemographicEntrySteps {

  private final Active<NameDemographic> activeName;

  NameDemographicEntrySteps(
      final Active<NameDemographic> activeName
  ) {
    this.activeName = activeName;
  }

  @Given("I am entering a {nameUse} name as of {localDate}")
  public void i_am_entering_a_name_as_of(final String use, final LocalDate asOf) {
    this.activeName.active(new NameDemographic(asOf, use));
  }

  @Given("I enter the prefix {namePrefix} on the current name")
  public void i_add_the_prefix(final String value) {
    this.activeName.active(current -> current.withPrefix(value));
  }

  @Given("I enter the first name {string} on the current name")
  public void i_add_the_first_name(final String value) {
    this.activeName.active(current -> current.withFirst(value));
  }

  @Given("I enter the middle name {string} on the current name")
  public void i_add_the_middle_name(final String value) {
    this.activeName.active(current -> current.withMiddle(value));
  }

  @Given("I enter the second middle name {string} on the current name")
  public void i_add_the_second_middle_name(final String value) {
    this.activeName.active(current -> current.withSecondMiddle(value));
  }

  @Given("I enter the last name {string} on the current name")
  public void i_add_the_last_name(final String value) {
    this.activeName.active(current -> current.withLast(value));
  }

  @Given("I enter the second last name {string} on the current name")
  public void i_add_the_second_last_name(final String value) {
    this.activeName.active(current -> current.withSecondLast(value));
  }

  @Given("I enter the suffix {nameSuffix} on the current name")
  public void i_add_the_suffix(final String value) {
    this.activeName.active(current -> current.withSuffix(value));
  }

  @Given("I enter the degree {degree} on the current name")
  public void i_add_the_degree(final String value) {
    this.activeName.active(current -> current.withDegree(value));
  }

}
