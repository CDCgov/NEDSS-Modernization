package gov.cdc.nbs.event.report.lab.test;

import gov.cdc.nbs.event.report.lab.LabReportIdentifier;
import gov.cdc.nbs.event.report.morbidity.MorbidityReportIdentifier;
import gov.cdc.nbs.testing.support.Active;
import io.cucumber.java.en.Given;

public class CodedResultedTestSteps {

  private final Active<LabReportIdentifier> activeLab;
  private final CodedResultedTestMother testMother;
  private final Active<MorbidityReportIdentifier> activeMorbidity;
  private final MorbidityLabReportMother reportMother;

  CodedResultedTestSteps(
      final Active<LabReportIdentifier> activeLab,
      final Active<MorbidityReportIdentifier> activeMorbidity,
      final CodedResultedTestMother testMother,
      final MorbidityLabReportMother reportMother
  ) {
    this.activeLab = activeLab;
    this.activeMorbidity = activeMorbidity;
    this.testMother = testMother;
    this.reportMother = reportMother;
  }

  @Given("the laboratory report has a(n) {labTest} test with a coded result of {labResult}")
  @Given("the lab report has a(n) {labTest} test with a coded result of {labResult}")
  public void createForLab(final String test, final String result) {
    this.activeLab.maybeActive().ifPresent(
        found -> testMother.create(found, test, result)
    );
  }

  @Given("the morbidity report has a(n) {labTest} test with a coded result of {labResult}")
  public void createForMorbidity(final String test, final String result) {
    this.activeMorbidity.maybeActive()
        .map(reportMother::create)
        .ifPresent(
            found -> testMother.create(found, test, result)
        );
  }
}
