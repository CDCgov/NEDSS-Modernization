package gov.cdc.nbs.event.report.lab;

import gov.cdc.nbs.testing.support.concept.ConceptParameterResolver;
import io.cucumber.java.ParameterType;

public class SpecimenSteps {

  private final ConceptParameterResolver resolver;

  SpecimenSteps(final ConceptParameterResolver resolver) {
    this.resolver = resolver;
  }

  @ParameterType(name = "specimenSite", value = ".*")
  public String specimenSite(final String value) {
    return resolver.resolve("ANATOMIC_SITE", value)
        .orElse(value);
  }

  @ParameterType(name = "specimenSource", value = ".*")
  public String specimenSource(final String value) {
    return resolver.resolve("SPECMN_SRC", value)
        .orElse(value);
  }
}
