package gov.cdc.nbs.support.provider;

import io.cucumber.java.Before;
import io.cucumber.java.en.Given;

public class ProviderSteps {

  private final ProviderMother mother;

  ProviderSteps(final ProviderMother mother) {
    this.mother = mother;
  }

  @Before
  public void reset() {
    this.mother.reset();
  }

  @Given("there is a provider named {string} {string}")
  public void there_is_a_provider_named(final String first, final String last) {
    this.mother.create(first, last);
  }
}
