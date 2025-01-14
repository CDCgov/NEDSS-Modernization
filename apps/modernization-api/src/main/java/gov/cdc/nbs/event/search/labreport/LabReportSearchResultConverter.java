package gov.cdc.nbs.event.search.labreport;

import gov.cdc.nbs.event.search.RelevanceResolver;
import gov.cdc.nbs.patient.documentsrequiringreview.detail.LabTestSummary;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

class LabReportSearchResultConverter {

  static LabReportSearchResult convert(final SearchableLabReport searchable, final Double score,
      final LabTestSummaryFinder labTestSummaryFinder) {

    double relevance = RelevanceResolver.resolve(score);

    List<LabReportSearchResult.PersonParticipation> personParticipations = searchable.people()
        .stream().map(LabReportSearchResultConverter::asPerson)
        .toList();

    List<LabReportSearchResult.OrganizationParticipation> organizationParticipations = searchable.organizations()
        .stream()
        .map(LabReportSearchResultConverter::asOrganization)
        .toList();

    List<LabReportSearchResult.Observation> observations = searchable.tests()
        .stream()
        .map(LabReportSearchResultConverter::asObservation)
        .toList();

    List<LabReportSearchResult.AssociatedInvestigation> associatedInvestigations =
        asAssociatedInvestigations(searchable.associated());

    return new LabReportSearchResult(
        relevance,
        String.valueOf(searchable.identifier()),
        searchable.jurisdiction(),
        searchable.local(),
        searchable.createdOn(),
        personParticipations,
        organizationParticipations,
        observations,
        associatedInvestigations,
        labTestSummaryFinder == null ? new ArrayList<LabTestSummary>()
            : labTestSummaryFinder.find(searchable.identifier()));
  }

  private static LabReportSearchResult.PersonParticipation asPerson(final SearchableLabReport.Person person) {
    return person instanceof SearchableLabReport.Person.Patient patient
        ? asPerson(patient)
        : asPerson((SearchableLabReport.Person.Provider) person);
  }

  private static LabReportSearchResult.PersonParticipation asPerson(final SearchableLabReport.Person.Patient patient) {
    return new LabReportSearchResult.PersonParticipation(
        patient.birthday(),
        patient.gender(),
        patient.type(),
        patient.firstName(),
        patient.lastName(),
        patient.code(),
        patient.identifier(),
        patient.local());
  }

  private static LabReportSearchResult.PersonParticipation asPerson(
      final SearchableLabReport.Person.Provider provider) {
    return new LabReportSearchResult.PersonParticipation(
        null,
        null,
        provider.type(),
        provider.firstName(),
        provider.lastName(),
        provider.code(),
        provider.identifier(),
        null);
  }

  private static LabReportSearchResult.OrganizationParticipation asOrganization(
      final SearchableLabReport.Organization organization) {
    return new LabReportSearchResult.OrganizationParticipation(
        organization.type(),
        organization.name());
  }

  private static LabReportSearchResult.Observation asObservation(final SearchableLabReport.LabTest test) {
    return new LabReportSearchResult.Observation(
        test.name(),
        test.alternative(),
        test.result());
  }

  private static List<LabReportSearchResult.AssociatedInvestigation> asAssociatedInvestigations(
      final List<SearchableLabReport.Investigation> investigations) {
    return investigations == null
        ? Collections.emptyList()
        : investigations.stream()
            .map(LabReportSearchResultConverter::asAssociatedInvestigation)
            .toList();
  }

  private static LabReportSearchResult.AssociatedInvestigation asAssociatedInvestigation(
      final SearchableLabReport.Investigation investigation) {
    return new LabReportSearchResult.AssociatedInvestigation(
        investigation.condition(),
        investigation.local());
  }

  private LabReportSearchResultConverter() {

  }

}
