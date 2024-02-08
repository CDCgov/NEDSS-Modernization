package gov.cdc.nbs.option.provider.autocomplete;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.en.Given;

public class ProviderOptionSteps {

  private final ProviderOptionMother mother;

  ProviderOptionSteps(final ProviderOptionMother mother) {
    this.mother = mother;
  }

  @After("@providers")
  @Before("@providers")
  public void clean() {
    mother.reset();
  }

  @Given("there is a provider for {string} {string}")
  public void there_is_a_condition(final String first, final String last) {
    mother.create(first, last);
  }

}
