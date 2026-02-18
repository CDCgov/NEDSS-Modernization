package gov.cdc.nbs.support.concept.condition;

import io.cucumber.java.ParameterType;

public class ConditionSteps {

  private final ConditionParameterResolver resolver;

  ConditionSteps(final ConditionParameterResolver resolver) {
    this.resolver = resolver;
  }

  @ParameterType(name = "condition", value = ".*")
  public String raceCategory(final String value) {
    return resolver.resolve(value).orElse(value);
  }
}
