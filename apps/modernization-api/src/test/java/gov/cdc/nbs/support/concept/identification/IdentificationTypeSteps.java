package gov.cdc.nbs.support.concept.identification;

import gov.cdc.nbs.testing.support.concept.ConceptParameterResolver;
import io.cucumber.java.ParameterType;

public class IdentificationTypeSteps {

  private static final String IDENTIFICATION_TYPE_CODE_SET = "EI_TYPE_PAT";
  private final ConceptParameterResolver resolver;

  IdentificationTypeSteps(final ConceptParameterResolver resolver) {
    this.resolver = resolver;
  }

  @ParameterType(name = "identificationType", value = "\".*\"|.*")
  public String identificationType(final String type) {
    String adjusted = type.replaceAll("\"", "");
    return this.resolver.resolve(
        IDENTIFICATION_TYPE_CODE_SET,
        adjusted
    ).orElse(adjusted);
  }

}
