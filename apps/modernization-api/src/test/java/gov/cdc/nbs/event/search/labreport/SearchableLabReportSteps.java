package gov.cdc.nbs.event.search.labreport;

import gov.cdc.nbs.event.report.lab.LabReportIdentifier;
import gov.cdc.nbs.patient.identifier.PatientIdentifier;
import gov.cdc.nbs.testing.support.Active;
import gov.cdc.nbs.testing.support.Available;
import io.cucumber.java.en.Given;

public class SearchableLabReportSteps {

  private final Active<SearchableLabReport> active;
  private final Available<SearchableLabReport> available;
  private final Available<LabReportIdentifier> labs;
  private final SearchableLabReportMother mother;

  SearchableLabReportSteps(
      final Active<PatientIdentifier> patient,
      final Active<SearchableLabReport> active,
      final Available<SearchableLabReport> available,
      final Available<LabReportIdentifier> labs,
      final SearchableLabReportMother mother
  ) {
    this.active = active;
    this.available = available;
    this.labs = labs;
    this.mother = mother;
  }

  @Given("lab reports are available for search")
  public void lab_reports_are_available_for_search() {
    this.mother.searchable(this.labs.all());
  }

  @Given("I am searching for one of the Lab Reports")
  public void i_am_searching_for_one_of_the_lab_reports() {
    this.available.random().ifPresent(this.active::active);
  }
}
