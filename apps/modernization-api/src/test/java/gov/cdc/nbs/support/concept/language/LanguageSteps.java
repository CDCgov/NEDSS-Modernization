package gov.cdc.nbs.support.concept.language;

import io.cucumber.java.ParameterType;

public class LanguageSteps {

  private final LanguageParameterResolver resolver;

  LanguageSteps(final LanguageParameterResolver resolver) {
    this.resolver = resolver;
  }

  @ParameterType(name = "language", value = ".*")
  public String language(final String value) {
    return resolver.resolve(value).orElse(null);
  }
}
