package gov.cdc.nbs.option.provider.autocomplete;

import io.cucumber.java.en.Given;

public class ProviderOptionSteps {

  private final ProviderOptionMother mother;

  ProviderOptionSteps(final ProviderOptionMother mother) {
    this.mother = mother;
  }

  @Given("there is a provider for {string} {string}")
  public void there_is_a_provider(final String first, final String last) {
    mother.create(first, last);
  }

  @Given("there is a provider for {string} {string} that was added electronically")
  public void there_is_an_electronic_provider(final String first, final String last) {
    mother.electronic(first, last);
  }
}
