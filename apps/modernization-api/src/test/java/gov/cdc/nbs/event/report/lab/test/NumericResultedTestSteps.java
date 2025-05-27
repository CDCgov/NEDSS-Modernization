package gov.cdc.nbs.event.report.lab.test;

import gov.cdc.nbs.event.report.lab.LabReportIdentifier;
import gov.cdc.nbs.event.report.morbidity.MorbidityReportIdentifier;
import gov.cdc.nbs.testing.support.Active;
import io.cucumber.java.en.Given;

public class NumericResultedTestSteps {

  private final Active<LabReportIdentifier> activeLab;
  private final Active<MorbidityReportIdentifier> activeMorbidity;
  private final NumericResultedTestMother mother;

  NumericResultedTestSteps(
      final Active<LabReportIdentifier> activeLab,
      final Active<MorbidityReportIdentifier> activeMorbidity,
      final NumericResultedTestMother mother
  ) {
    this.activeLab = activeLab;
    this.activeMorbidity = activeMorbidity;
    this.mother = mother;
  }

  @Given("the lab(oratory) report has a(n) {labTest} test with a numeric result of {string} {unit}")
  public void createForLab(final String test, final String result, final String unit) {
    this.activeLab.maybeActive().ifPresent(
        found -> mother.create(found, test, result, unit)
    );
  }

  @Given("the morbidity report has a(n) {labTest} test with a numeric result of {string} {unit}")
  public void createForMorbidity(final String test, final String result, final String unit) {
    this.activeMorbidity.maybeActive().ifPresent(
        found -> mother.create(found, test, result, unit)
    );
  }
}
