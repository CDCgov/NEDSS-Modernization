package gov.cdc.nbs.support.concept;

import gov.cdc.nbs.testing.support.concept.ConceptParameterResolver;
import io.cucumber.java.ParameterType;

public class GenderSteps {

  private final ConceptParameterResolver resolver;

   GenderSteps(final ConceptParameterResolver resolve) {
    this.resolver = resolve;
  }

  @ParameterType(name = "sex", value = ".+")
  public String sex(final String value) {
    return resolver.resolve("SEX", value)
        .orElse(null);
  }

  @ParameterType(name = "sexUnknown", value = ".+")
  public String sexUnknown(final String value) {
    return resolver.resolve("SEX_UNK_REASON", value)
        .orElse(null);
  }

  @ParameterType(name = "transgender", value = ".+")
  public String transgender(final String value) {
    return resolver.resolve("NBS_STD_GENDER_PARPT", value)
        .orElse(null);
  }
}
