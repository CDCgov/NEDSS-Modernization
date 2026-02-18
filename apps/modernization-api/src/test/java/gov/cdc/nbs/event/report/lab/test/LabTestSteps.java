package gov.cdc.nbs.event.report.lab.test;

import io.cucumber.java.ParameterType;

public class LabTestSteps {

  private final LabTestParameterResolver resolver;

  LabTestSteps(final LabTestParameterResolver resolver) {
    this.resolver = resolver;
  }

  @ParameterType(name = "labTest", value = ".*")
  public String labTest(final String value) {
    return resolver.resolve(value).orElse(value);
  }
}
