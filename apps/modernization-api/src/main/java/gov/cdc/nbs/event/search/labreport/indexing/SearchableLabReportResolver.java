package gov.cdc.nbs.event.search.labreport.indexing;

import gov.cdc.nbs.event.search.labreport.SearchableLabReport;
import gov.cdc.nbs.event.search.labreport.indexing.identifier.SearchableLabReportIdentifierFinder;
import gov.cdc.nbs.event.search.labreport.indexing.investigation.SearchableLabReportInvestigationFinder;
import gov.cdc.nbs.event.search.labreport.indexing.organization.SearchableLabReportOrganizationFinder;
import gov.cdc.nbs.event.search.labreport.indexing.test.SearchableLabReportTestFinder;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Component;

@Component
public class SearchableLabReportResolver {

  private final SearchableLabReportFinder finder;
  private final SearchableLabReportPersonFinder personFinder;
  private final SearchableLabReportIdentifierFinder identifierFinder;
  private final SearchableLabReportInvestigationFinder investigationFinder;
  private final SearchableLabReportOrganizationFinder organizationFinder;
  private final SearchableLabReportTestFinder testFinder;

  SearchableLabReportResolver(
      final SearchableLabReportFinder finder,
      final SearchableLabReportPersonFinder personFinder,
      final SearchableLabReportIdentifierFinder identifierFinder,
      final SearchableLabReportInvestigationFinder investigationFinder,
      final SearchableLabReportOrganizationFinder organizationFinder,
      final SearchableLabReportTestFinder testFinder) {
    this.finder = finder;
    this.personFinder = personFinder;
    this.identifierFinder = identifierFinder;
    this.investigationFinder = investigationFinder;
    this.organizationFinder = organizationFinder;
    this.testFinder = testFinder;
  }

  public Optional<SearchableLabReport> resolve(final long identifier) {
    return this.finder.find(identifier).map(this::withLinkedInformation);
  }

  private SearchableLabReport withLinkedInformation(final SearchableLabReport lab) {

    List<SearchableLabReport.Person> people = this.personFinder.find(lab.identifier());
    List<SearchableLabReport.Organization> organizations =
        this.organizationFinder.find(lab.identifier());
    List<SearchableLabReport.LabTest> tests = this.testFinder.find(lab.identifier());
    List<SearchableLabReport.Identifier> identifiers = this.identifierFinder.find(lab.identifier());
    List<SearchableLabReport.Investigation> associated =
        this.investigationFinder.find(lab.identifier());

    return new SearchableLabReport(
        lab.identifier(),
        lab.classCode(),
        lab.mood(),
        lab.programArea(),
        lab.jurisdiction(),
        lab.oid(),
        lab.pregnancyStatus(),
        lab.local(),
        lab.reportedOn(),
        lab.collectedOn(),
        lab.receivedOn(),
        lab.createdBy(),
        lab.createdOn(),
        lab.updatedBy(),
        lab.updatedOn(),
        lab.version(),
        lab.status(),
        lab.electronicEntry(),
        people,
        organizations,
        tests,
        identifiers,
        associated);
  }
}
