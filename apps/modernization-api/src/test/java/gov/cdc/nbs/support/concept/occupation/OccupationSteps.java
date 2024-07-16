package gov.cdc.nbs.support.concept.occupation;

import io.cucumber.java.ParameterType;

public class OccupationSteps {

  private final OccupationParameterResolver resolver;

  OccupationSteps(final OccupationParameterResolver resolver) {
    this.resolver = resolver;
  }

  @ParameterType(name = "occupation", value = ".*")
  public String occupation(final String value) {
    return resolver.resolve(value)
        .orElse(null);
  }
}
