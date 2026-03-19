package gov.cdc.nbs.event.report.lab.test;

import io.cucumber.java.ParameterType;

public class LabResultSteps {

  private final LabResultParameterResolver resolver;

  LabResultSteps(final LabResultParameterResolver resolver) {
    this.resolver = resolver;
  }

  @ParameterType(name = "labResult", value = ".*")
  public String labResult(final String value) {
    return resolver.resolve(value).orElse(value);
  }
}
