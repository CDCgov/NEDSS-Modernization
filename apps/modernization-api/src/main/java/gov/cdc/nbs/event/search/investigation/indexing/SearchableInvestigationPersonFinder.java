package gov.cdc.nbs.event.search.investigation.indexing;

import gov.cdc.nbs.event.search.investigation.SearchableInvestigation;
import gov.cdc.nbs.event.search.investigation.indexing.patient.SearchableInvestigationPatientFinder;
import gov.cdc.nbs.event.search.investigation.indexing.provider.SearchableInvestigationProviderFinder;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
class SearchableInvestigationPersonFinder {

  private final SearchableInvestigationPatientFinder patientFinder;
  private final SearchableInvestigationProviderFinder providerFinder;

  SearchableInvestigationPersonFinder(
      final SearchableInvestigationPatientFinder patientFinder,
      final SearchableInvestigationProviderFinder providerFinder) {
    this.patientFinder = patientFinder;
    this.providerFinder = providerFinder;
  }

  List<SearchableInvestigation.Person> find(final long investigation) {
    List<SearchableInvestigation.Person.Patient> patients = this.patientFinder.find(investigation);
    List<SearchableInvestigation.Person.Provider> providers =
        this.providerFinder.find(investigation);

    ArrayList<SearchableInvestigation.Person> people =
        new ArrayList<>(patients.size() + providers.size());

    people.addAll(patients);
    people.addAll(providers);

    return people;
  }
}
