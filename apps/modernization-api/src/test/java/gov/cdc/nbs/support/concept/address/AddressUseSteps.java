package gov.cdc.nbs.support.concept.address;

import gov.cdc.nbs.testing.support.concept.ConceptParameterResolver;
import io.cucumber.java.ParameterType;

public class AddressUseSteps {

  private final ConceptParameterResolver resolver;

  AddressUseSteps(final ConceptParameterResolver resolver) {
    this.resolver = resolver;
  }

  @ParameterType(name = "addressUse", value = "\".*\"|.*")
  public String addressUse(final String type) {
    String adjusted = type.replaceAll("\"", "");
    return this.resolver.resolve(
        "EL_USE_PST_PAT",
        adjusted
    ).orElse(adjusted);
  }

}
