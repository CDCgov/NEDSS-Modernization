package gov.cdc.nbs.testing.authorization.jurisdiction;

import io.cucumber.java.ParameterType;
import io.cucumber.java.en.Given;

public class JurisdictionSteps {

  private final JurisdictionMother mother;
  private final JurisdictionParameterResolver resolver;

  JurisdictionSteps(
      final JurisdictionMother mother,
      final JurisdictionParameterResolver resolver
  ) {
    this.mother = mother;
    this.resolver = resolver;
  }

  @Given("there is a jurisdiction named {string}")
  public void create(final String name) {
    this.mother.create(name);
  }

  @ParameterType(name = "jurisdiction", value = "\"?([\\w -]*)\"?")
  public JurisdictionIdentifier jurisdiction(final String value) {
    return resolver.resolve(value).orElse(null);
  }

}
