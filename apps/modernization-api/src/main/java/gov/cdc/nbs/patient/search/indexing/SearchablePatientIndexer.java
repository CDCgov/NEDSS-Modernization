package gov.cdc.nbs.patient.search.indexing;

import gov.cdc.nbs.patient.search.SearchablePatient;
import gov.cdc.nbs.search.ElasticsearchSimpleDocumentIndexer;
import java.util.Collection;
import org.springframework.stereotype.Component;

@Component
class SearchablePatientIndexer {

  private static final String INDEX = "person";
  private final ElasticsearchSimpleDocumentIndexer indexer;

  SearchablePatientIndexer(final ElasticsearchSimpleDocumentIndexer indexer) {
    this.indexer = indexer;
  }

  void index(final SearchablePatient patient) {
    this.indexer.index(INDEX, SearchablePatientSimpleDocumentConverter.convert(patient));
  }

  void index(final Collection<SearchablePatient> patients) {
    this.indexer.index(
        INDEX, patients.stream().map(SearchablePatientSimpleDocumentConverter::convert));
  }
}
