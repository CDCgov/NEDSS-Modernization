package gov.cdc.nbs.support.jurisdiction;

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
  public void there_is_a_jurisdiction_named(final String name) {
    this.mother.create(name);
  }

  @ParameterType(name = "jurisdiction", value = ".*")
  public JurisdictionIdentifier jurisdiction(final String value) {
    return resolver.resolve(value).orElse(null);
  }

}
