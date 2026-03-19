package gov.cdc.nbs.event.search.investigation;

import gov.cdc.nbs.event.search.RelevanceResolver;
import java.util.List;

class InvestigationSearchResultConverter {

  static InvestigationSearchResult convert(
      final SearchableInvestigation searchable, final Double score) {
    double relevance = RelevanceResolver.resolve(score);

    List<InvestigationSearchResult.PersonParticipation> people =
        searchable.people().stream().map(InvestigationSearchResultConverter::asPerson).toList();

    return new InvestigationSearchResult(
        relevance,
        String.valueOf(searchable.identifier()),
        searchable.conditionName(),
        searchable.jurisdiction(),
        searchable.jurisdictionName(),
        searchable.local(),
        searchable.createdOn(),
        searchable.startedOn(),
        searchable.status(),
        searchable.notificationStatus(),
        people);
  }

  private static InvestigationSearchResult.PersonParticipation asPerson(
      final SearchableInvestigation.Person person) {
    return person instanceof SearchableInvestigation.Person.Patient patient
        ? asPerson(patient)
        : asPerson((SearchableInvestigation.Person.Provider) person);
  }

  private static InvestigationSearchResult.PersonParticipation asPerson(
      final SearchableInvestigation.Person.Patient patient) {
    return new InvestigationSearchResult.PersonParticipation(
        patient.birthday(),
        patient.gender(),
        patient.type(),
        patient.firstName(),
        patient.lastName(),
        patient.code(),
        patient.identifier(),
        patient.local());
  }

  private static InvestigationSearchResult.PersonParticipation asPerson(
      final SearchableInvestigation.Person.Provider provider) {
    return new InvestigationSearchResult.PersonParticipation(
        null,
        null,
        provider.type(),
        provider.firstName(),
        provider.lastName(),
        provider.code(),
        provider.identifier(),
        null);
  }

  private InvestigationSearchResultConverter() {}
}
