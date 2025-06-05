package gov.cdc.nbs.support.concept.identification;

import gov.cdc.nbs.testing.support.concept.ConceptParameterResolver;
import io.cucumber.java.ParameterType;

public class IdentificationTypeSteps {

  private static final String IDENTIFICATION_TYPE_CODE_SET = "EI_TYPE_PAT";
  private static final String AUTHORITY_CODE_SET = "EI_AUTH_PAT";

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

  @ParameterType(name = "assigningAuthority", value = ".*")
  public String assigningAuthority(final String type) {
    return this.resolver.resolve(
        AUTHORITY_CODE_SET,
        type
    ).orElse(type);
  }

}
