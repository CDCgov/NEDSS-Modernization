package gov.cdc.nbs.support.provider;

import io.cucumber.java.en.Given;

public class ProviderSteps {

  private final ProviderMother mother;

  ProviderSteps(final ProviderMother mother) {
    this.mother = mother;
  }

  @Given("there is a provider named {string} {string}")
  public void named(final String first, final String last) {
    this.mother.create(null, first, last);
  }

  @Given("there is a provider named {namePrefix} {string} {string}")
  public void named(final String prefix, final String first, final String last) {
    this.mother.create(prefix, first, last);
  }
}
