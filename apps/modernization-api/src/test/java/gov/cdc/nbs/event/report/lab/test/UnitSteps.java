package gov.cdc.nbs.event.report.lab.test;

import gov.cdc.nbs.testing.support.concept.ConceptParameterResolver;
import io.cucumber.java.ParameterType;

public class UnitSteps {
  private static final String SET = "UNIT_ISO";
  private final ConceptParameterResolver resolver;

  UnitSteps(final ConceptParameterResolver resolver) {
    this.resolver = resolver;
  }

  @ParameterType(name = "unit", value = ".*")
  public String educationLevel(final String value) {
    return resolver.resolve(SET, value).orElse(value);
  }
}
