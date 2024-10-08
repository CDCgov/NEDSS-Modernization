package gov.cdc.nbs.event.search.investigation.indexing;

import gov.cdc.nbs.event.search.investigation.SearchableInvestigation;
import gov.cdc.nbs.event.search.investigation.indexing.identifier.SearchableInvestigationIdentifierFinder;
import gov.cdc.nbs.event.search.investigation.indexing.organization.SearchableInvestigationOrganizationFinder;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class SearchableInvestigationResolver {

  private final SearchableInvestigationFinder finder;
  private final SearchableInvestigationPersonFinder personFinder;
  private final SearchableInvestigationOrganizationFinder organizationFinder;
  private final SearchableInvestigationIdentifierFinder identifierFinder;

  SearchableInvestigationResolver(
      final SearchableInvestigationFinder finder,
      final SearchableInvestigationPersonFinder personFinder,
      final SearchableInvestigationOrganizationFinder organizationFinder,
      final SearchableInvestigationIdentifierFinder identifierFinder) {
    this.finder = finder;
    this.personFinder = personFinder;
    this.organizationFinder = organizationFinder;
    this.identifierFinder = identifierFinder;
  }

  public Optional<SearchableInvestigation> resolve(final long identifier) {
    return this.finder.find(identifier).map(this::withLinkedInformation);
  }

  private SearchableInvestigation withLinkedInformation(final SearchableInvestigation investigation) {

    List<SearchableInvestigation.Person> people = this.personFinder.find(investigation.identifier());
    List<SearchableInvestigation.Organization> organizations = this.organizationFinder.find(investigation.identifier());
    List<SearchableInvestigation.Identifier> identifiers = this.identifierFinder.find(investigation.identifier());

    return new SearchableInvestigation(
        investigation.identifier(),
        investigation.classCode(),
        investigation.mood(),
        investigation.programArea(),
        investigation.jurisdiction(),
        investigation.jurisdictionName(),
        investigation.oid(),
        investigation.caseClass(),
        investigation.caseType(),
        investigation.outbreak(),
        investigation.conditionName(),
        investigation.condition(),
        investigation.pregnancyStatus(),
        investigation.local(),
        investigation.createdBy(),
        investigation.createdOn(),
        investigation.updatedBy(),
        investigation.updatedOn(),
        investigation.reportedOn(),
        investigation.startedOn(),
        investigation.closedOn(),
        investigation.processing(),
        investigation.status(),
        investigation.notification(),
        investigation.notifiedOn(),
        investigation.notificationStatus(),
        investigation.investigatorLastName(),
        people,
        organizations,
        identifiers);
  }
}
