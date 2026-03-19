package gov.cdc.nbs.support.concept.address;

import gov.cdc.nbs.testing.support.concept.ConceptParameterResolver;
import io.cucumber.java.ParameterType;

public class AddressTypeSteps {

  private final ConceptParameterResolver resolver;

  AddressTypeSteps(final ConceptParameterResolver resolver) {
    this.resolver = resolver;
  }

  @ParameterType(name = "addressType", value = "\".*\"|.*")
  public String addressType(final String type) {
    String adjusted = type.replaceAll("\"", "");
    return this.resolver.resolve("EL_TYPE_PST_PAT", adjusted).orElse(adjusted);
  }
}
