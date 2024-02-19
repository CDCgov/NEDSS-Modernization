package gov.cdc.nbs.patient.search.indexing;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch.core.IndexResponse;
import gov.cdc.nbs.patient.search.SearchablePatient;
import org.springframework.stereotype.Component;

import java.io.IOException;


@Component
class SearchablePatientIndexer {

  private static final System.Logger LOGGER = System.getLogger(SearchablePatientIndexer.class.getName());

  private final ElasticsearchClient client;

  SearchablePatientIndexer(final ElasticsearchClient client) {
    this.client = client;
  }

  void index(final SearchablePatient document) {

    try {
      String identifier = String.valueOf(document.identifier());

      IndexResponse response = client.index(
          index -> index.document("person")
              .id(identifier)
              .document(document)
      );

    } catch (IOException exception) {
      LOGGER.log(
          System.Logger.Level.ERROR,
          () -> String.format("Unable to index patient %s", document.identifier()),
          exception
      );
    }
  }
}
