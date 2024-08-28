package gov.cdc.nbs.support.concept;

import gov.cdc.nbs.testing.support.concept.ConceptParameterResolver;
import io.cucumber.java.ParameterType;

public class EducationLevelSteps {

  private static final String SET = "P_EDUC_LVL";
  private final ConceptParameterResolver resolver;

  EducationLevelSteps(final ConceptParameterResolver resolver) {
    this.resolver = resolver;
  }

  @ParameterType(name = "educationLevel", value = ".*")
  public String educationLevel(final String value) {
    return resolver.resolve(SET, value)
        .orElse(null);
  }
}
