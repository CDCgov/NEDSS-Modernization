package gov.cdc.nbs.event.report.lab.test;

import gov.cdc.nbs.event.report.lab.LabReportIdentifier;
import gov.cdc.nbs.testing.support.Active;
import io.cucumber.java.en.Given;

public class ResultedTestSteps {

  private final Active<LabReportIdentifier> lab;
  private final CodedResultedTestMother mother;

  ResultedTestSteps(
      final Active<LabReportIdentifier> lab,
      final CodedResultedTestMother mother
  ) {
    this.lab = lab;
    this.mother = mother;
  }

  @Given("the lab report has a(n) {labTest} test with a coded result of {labResult}")
  public void test(final String test, final String result) {
      this.lab.maybeActive().ifPresent(
          found -> mother.create(found, test, result)
      );
  }

}
