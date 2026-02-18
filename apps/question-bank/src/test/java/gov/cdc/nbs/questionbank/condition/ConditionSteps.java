package gov.cdc.nbs.questionbank.condition;

import io.cucumber.java.ParameterType;

public class ConditionSteps {

  private final ConditionParameterResolver resolver;

  ConditionSteps(final ConditionParameterResolver resolver) {
    this.resolver = resolver;
  }

  @ParameterType(name = "condition", value = ".*")
  public String condition(final String value) {
    return resolver.resolve(value).orElse(null);
  }
}
