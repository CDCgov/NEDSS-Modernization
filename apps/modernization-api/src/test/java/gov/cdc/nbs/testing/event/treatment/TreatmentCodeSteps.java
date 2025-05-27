package gov.cdc.nbs.testing.event.treatment;

import io.cucumber.java.ParameterType;

public class TreatmentCodeSteps {

  private final TreatmentCodeParameterResolver resolver;

  TreatmentCodeSteps(final TreatmentCodeParameterResolver resolver) {
    this.resolver = resolver;
  }

  @ParameterType(name = "treatment", value = ".*")
  public String treatment(final String value) {
    return resolver.resolve(value).orElse(null);
  }
}
