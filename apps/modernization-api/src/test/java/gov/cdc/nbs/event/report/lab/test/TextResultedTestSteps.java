package gov.cdc.nbs.event.report.lab.test;

import gov.cdc.nbs.event.report.lab.LabReportIdentifier;
import gov.cdc.nbs.event.report.morbidity.MorbidityReportIdentifier;
import gov.cdc.nbs.testing.support.Active;
import io.cucumber.java.en.Given;

public class TextResultedTestSteps {

  private final Active<LabReportIdentifier> activeLab;
  private final Active<MorbidityReportIdentifier> activeMorbidity;
  private final TextResultedTestMother mother;

  TextResultedTestSteps(
      final Active<LabReportIdentifier> activeLab,
      final Active<MorbidityReportIdentifier> activeMorbidity,
      final TextResultedTestMother mother
  ) {
    this.activeLab = activeLab;
    this.activeMorbidity = activeMorbidity;
    this.mother = mother;
  }

  @Given("the lab(oratory) report has a(n) {labTest} test with a text result of {string}")
  public void createForLab(final String test, final String result) {
    this.activeLab.maybeActive().ifPresent(
        found -> mother.create(found, test, result)
    );
  }

  @Given("the morbidity report has a(n) {labTest} test with a text result of {string}")
  public void createForMorbidity(final String test, final String result) {
    this.activeMorbidity.maybeActive().ifPresent(
        found -> mother.create(found, test, result)
    );
  }
}
