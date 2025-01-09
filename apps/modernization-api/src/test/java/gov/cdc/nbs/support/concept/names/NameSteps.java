package gov.cdc.nbs.support.concept.names;

import gov.cdc.nbs.testing.support.concept.ConceptParameterResolver;
import io.cucumber.java.ParameterType;

public class NameSteps {

  private final ConceptParameterResolver resolver;

  public NameSteps(ConceptParameterResolver resolver) {
    this.resolver = resolver;
  }

  @ParameterType(name = "nameUse", value = ".*")
  public String nameUse(final String value) {
    return resolver.resolve("P_NM_USE", value)
        .orElse(null);
  }

  @ParameterType(name = "namePrefix", value = ".*")
  public String namePrefix(final String value) {
    return resolver.resolve("P_NM_PFX", value)
        .orElse(null);
  }

  @ParameterType(name = "nameSuffix", value = ".*")
  public String nameSuffix(final String value) {
    return resolver.resolve("P_NM_SFX", value)
            .orElse(null);
  }

  @ParameterType(name = "degree", value = ".*")
  public String degree(final String value) {
    return resolver.resolve("P_NM_DEG", value)
        .orElse(null);
  }
}
