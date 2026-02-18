package gov.cdc.nbs.event.report.lab;

import gov.cdc.nbs.testing.support.Active;
import io.cucumber.java.en.Given;

public class LabReportOrderedTestSteps {

  private final Active<LabReportIdentifier> activeReport;
  private final LabReportMother reportMother;
  private final MaterialMother materialMother;

  LabReportOrderedTestSteps(
      final Active<LabReportIdentifier> activeReport,
      final LabReportMother reportMother,
      final MaterialMother materialMother) {
    this.activeReport = activeReport;
    this.reportMother = reportMother;
    this.materialMother = materialMother;
  }

  @Given("the laboratory report has an ordered test with a specimen from the {specimenSite}")
  public void specimenSite(final String site) {
    specimen(null, site);
  }

  @Given(
      "the laboratory report has an ordered test with a {specimenSource} specimen from the {specimenSite}")
  public void specimen(final String source, final String site) {
    activeReport.maybeActive().ifPresent(report -> reportMother.specimen(report, site));

    if (source != null) {
      activeReport.maybeActive().ifPresent(report -> materialMother.create(report, source));
    }
  }
}
