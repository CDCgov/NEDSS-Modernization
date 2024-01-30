package gov.cdc.nbs.support.organization;

import io.cucumber.java.ParameterType;
import io.cucumber.java.en.Given;

public class OrganizationSteps {

  private final OrganizationMother mother;
  private final OrganizationParameterResolver resolver;

  OrganizationSteps(
      final OrganizationMother mother,
      final OrganizationParameterResolver resolver
  ) {
    this.mother = mother;
    this.resolver = resolver;
  }

  public void reset() {
    this.mother.reset();
  }

  @Given("there is an organization named {string}")
  public void there_is_an_organization(final String name) {
    this.mother.create(name);
  }

  @ParameterType(name = "organization", value = ".*")
  public long organization(final String value) {
    return resolver.resolve(value).orElse(-1L);
  }

}
