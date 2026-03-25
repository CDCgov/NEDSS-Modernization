package gov.cdc.nbs.configuration.nbs;

import io.cucumber.java.en.Given;

public class NBSConfigurationSteps {

  private final NBSConfigurationMother mother;

  NBSConfigurationSteps(final NBSConfigurationMother mother) {
    this.mother = mother;
  }

  @Given("NBS is configured with a(n) {string} of {string}")
  public void nbs_is_configured_with_a(final String key, final String value) {
    mother.set(key, value);
  }
}
