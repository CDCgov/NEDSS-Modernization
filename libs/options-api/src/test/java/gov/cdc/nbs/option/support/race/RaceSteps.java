package gov.cdc.nbs.option.support.race;

import gov.cdc.nbs.testing.support.concept.ConceptParameterResolver;
import io.cucumber.java.ParameterType;

public class RaceSteps {

  private final ConceptParameterResolver resolver;

  RaceSteps(
      final ConceptParameterResolver resolver
  ) {
    this.resolver = resolver;
  }

  @ParameterType(name = "raceCategory", value = ".*")
  public String raceCategory(final String value) {
    return resolver.resolve("RACE_CALCULATED", value)
        .orElse(null);
  }

}
