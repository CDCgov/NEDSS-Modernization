package gov.cdc.nbs.patient.search.indexing;

import org.springframework.stereotype.Component;

@Component
public class PatientIndexer {

  private final SearchablePatientResolver resolver;
  private final SearchablePatientIndexer indexer;

  PatientIndexer(final SearchablePatientResolver resolver, final SearchablePatientIndexer indexer) {
    this.resolver = resolver;
    this.indexer = indexer;
  }

  public void index(final long patient) {
    this.resolver.resolve(patient).ifPresent(this.indexer::index);
  }
}
