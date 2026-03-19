package gov.cdc.nbs.event.search.labreport;

import gov.cdc.nbs.event.report.lab.LabReportIdentifier;
import gov.cdc.nbs.testing.support.Active;
import gov.cdc.nbs.testing.support.Available;
import io.cucumber.java.en.Given;
import java.util.Objects;

public class SearchableLabReportSteps {

  private final Active<SearchableLabReport> active;
  private final Available<SearchableLabReport> available;
  private final Active<LabReportIdentifier> lab;
  private final Available<LabReportIdentifier> labs;
  private final SearchableLabReportMother mother;

  SearchableLabReportSteps(
      final Active<SearchableLabReport> active,
      final Available<SearchableLabReport> available,
      final Active<LabReportIdentifier> lab,
      final Available<LabReportIdentifier> labs,
      final SearchableLabReportMother mother) {
    this.active = active;
    this.available = available;
    this.lab = lab;
    this.labs = labs;
    this.mother = mother;
  }

  @Given("the lab report is available for search")
  public void lab_report_is_available_for_search() {
    this.lab.maybeActive().ifPresent(mother::searchable);
  }

  @Given("lab reports are available for search")
  public void lab_reports_are_available_for_search() {
    this.mother.searchable(this.labs.all());
  }

  @Given("I am searching for the Lab Report")
  public void i_am_searching_the_lab_report() {
    this.lab.maybeActive().stream()
        .flatMap(
            current ->
                this.available
                    .all()
                    .filter(
                        searchable ->
                            Objects.equals(current.identifier(), searchable.identifier())))
        .findFirst()
        .ifPresent(this.active::active);
  }
}
