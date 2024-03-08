package gov.cdc.nbs.event.search.labreport.indexing;

import gov.cdc.nbs.event.search.labreport.SearchableLabReport;
import gov.cdc.nbs.event.search.labreport.indexing.patient.SearchableLabReportPatientFinder;
import gov.cdc.nbs.event.search.labreport.indexing.provider.SearchableLabReportProviderFinder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
class SearchableLabReportPersonFinder {

  private final SearchableLabReportPatientFinder patientFinder;
  private final SearchableLabReportProviderFinder providerFinder;

  SearchableLabReportPersonFinder(
      final SearchableLabReportPatientFinder patientFinder,
      final SearchableLabReportProviderFinder providerFinder
  ) {
    this.patientFinder = patientFinder;
    this.providerFinder = providerFinder;
  }

  List<SearchableLabReport.Person> find(final long lab) {
    List<SearchableLabReport.Person.Patient> patients = this.patientFinder.find(lab);
    List<SearchableLabReport.Person.Provider> providers = this.providerFinder.find(lab);

    ArrayList<SearchableLabReport.Person> people = new ArrayList<>(patients.size() + providers.size());

    people.addAll(patients);
    people.addAll(providers);

    return people;
  }
}
