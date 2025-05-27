package gov.cdc.nbs.testing.event.treatment;

import gov.cdc.nbs.event.investigation.InvestigationIdentifier;
import gov.cdc.nbs.event.report.morbidity.MorbidityReportIdentifier;
import gov.cdc.nbs.testing.support.Active;
import io.cucumber.java.en.Given;

public class TreatmentSteps {

  private final TreatmentMother mother;
  private final Active<MorbidityReportIdentifier> activeMorbidity;
  private final Active<InvestigationIdentifier> activeInvestigation;

  TreatmentSteps(
      final TreatmentMother mother,
      final Active<MorbidityReportIdentifier> activeMorbidity,
      final Active<InvestigationIdentifier> activeInvestigation
  ) {
    this.mother = mother;
    this.activeMorbidity = activeMorbidity;
    this.activeInvestigation = activeInvestigation;
  }

  @Given("the patient is a subject of a Treatment")
  public void subjectOf() {
    this.activeInvestigation.maybeActive().ifPresent(mother::create);
  }

  @Given("the morbidity report includes a {treatment} treatment")
  public void treatedOnMorbidity(final String treatment) {
    this.activeMorbidity.maybeActive().ifPresent(report -> mother.create(report, treatment));
  }

  @Given("the morbidity report includes the custom {string} treatment")
  public void customTreatedOnMorbidity(final String treatment) {
    this.activeMorbidity.maybeActive().ifPresent(report -> mother.create(report, "OTH", treatment));
  }
}
