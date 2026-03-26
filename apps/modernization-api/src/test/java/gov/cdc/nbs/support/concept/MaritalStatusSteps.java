package gov.cdc.nbs.support.concept;

import gov.cdc.nbs.testing.support.concept.ConceptParameterResolver;
import io.cucumber.java.ParameterType;

public class MaritalStatusSteps {

  private static final String SET = "P_MARITAL";
  private final ConceptParameterResolver resolver;

  MaritalStatusSteps(final ConceptParameterResolver resolver) {
    this.resolver = resolver;
  }

  @ParameterType(name = "maritalStatus", value = ".*")
  public String maritalStatus(final String value) {
    return resolver.resolve(SET, value).orElse(null);
  }
}
