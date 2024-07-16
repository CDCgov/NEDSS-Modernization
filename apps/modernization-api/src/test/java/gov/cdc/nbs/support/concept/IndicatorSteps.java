package gov.cdc.nbs.support.concept;

import gov.cdc.nbs.testing.support.concept.ConceptParameterResolver;
import io.cucumber.java.ParameterType;

public class IndicatorSteps {

  private static final String SET = "YNU";
  private final ConceptParameterResolver resolver;

  IndicatorSteps(final ConceptParameterResolver resolver) {
    this.resolver = resolver;
  }

  @ParameterType(name = "indicator", value = ".*")
  public String indicator(final String value) {
    return switch (value.toLowerCase()) {
      case "does not", "doesn't" -> "N";
      case "does" -> "Y";
      default -> resolver.resolve(SET, value)
          .orElse(null);
    };

  }
}
