package gov.cdc.nbs.support.concept.phones;

import gov.cdc.nbs.testing.support.concept.ConceptParameterResolver;
import io.cucumber.java.ParameterType;

public class PhoneSteps {

  private final ConceptParameterResolver resolver;

  PhoneSteps(ConceptParameterResolver resolver) {
    this.resolver = resolver;
  }

  @ParameterType(name = "phoneType", value = ".*")
  public String phoneType(final String value) {
    return resolver.resolve("EL_TYPE_TELE_PAT", value)
        .orElse(null);
  }

  @ParameterType(name = "phoneUse", value = ".*")
  public String phoneUse(final String value) {
    return resolver.resolve("EL_USE_TELE_PAT", value)
        .orElse(null);
  }


}
