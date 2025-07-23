package gov.cdc.nbs.event.vaccination;

import gov.cdc.nbs.testing.support.concept.ConceptParameterResolver;
import io.cucumber.java.ParameterType;

public class VaccineSteps {
  private static final String SET = "VAC_NM";
  private final ConceptParameterResolver resolver;

  VaccineSteps(final ConceptParameterResolver resolver) {
    this.resolver = resolver;
  }

  @ParameterType(name = "vaccine", value = ".*")
  public String vaccine(final String value) {
    return resolver.resolve(SET, value)
        .orElse(value);
  }
}
