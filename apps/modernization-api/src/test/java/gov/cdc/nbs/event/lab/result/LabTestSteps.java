package gov.cdc.nbs.event.lab.result;

import io.cucumber.java.ParameterType;

public class LabTestSteps {

  private final LabTestParameterResolver testResolver;
  private final LabResultParameterResolver resultResolver;

  LabTestSteps(
      final LabTestParameterResolver testResolver,
      final LabResultParameterResolver resultResolver
  ) {
    this.testResolver = testResolver;
    this.resultResolver = resultResolver;
  }

  @ParameterType(name = "labTest", value = ".*")
  public String labTest(final String value) {
    return testResolver.resolve(value).orElse(value);
  }

  @ParameterType(name = "labResult", value = ".*")
  public String labResult(final String value) {
    return resultResolver.resolve(value).orElse(value);
  }
}
