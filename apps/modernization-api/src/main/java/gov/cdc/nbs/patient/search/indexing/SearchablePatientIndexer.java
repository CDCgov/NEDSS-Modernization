package gov.cdc.nbs.patient.search.indexing;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch._types.Refresh;
import co.elastic.clients.elasticsearch.core.BulkRequest;
import gov.cdc.nbs.patient.search.SearchablePatient;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Collection;
import java.util.stream.Stream;


@Component
class SearchablePatientIndexer {

  private static final System.Logger LOGGER = System.getLogger(SearchablePatientIndexer.class.getName());

  private final ElasticsearchClient client;
  private final String index;

  SearchablePatientIndexer(final ElasticsearchClient client) {
    this.client = client;
    this.index = "person";
  }

  void index(final SearchablePatient patient) {
    single(asDocument(patient));
  }

  void index(final Collection<SearchablePatient> patients) {
    multi(patients.stream().map(this::asDocument));
  }

  record Document(String identifier, Object payload) {

  }

  private Document asDocument(final SearchablePatient searchable) {
    String identifier = String.valueOf(searchable.identifier());
    return new Document(
        identifier,
        searchable
    );
  }

  private void single(final Document document) {

    try {
      client.index(
          indexing -> indexing.index(index)
              .id(document.identifier())
              .document(document.payload())
              .refresh(Refresh.True)
      );
    } catch (IOException exception) {
      LOGGER.log(
          System.Logger.Level.ERROR,
          () -> String.format("Unable to index %s (%s)", index, document.identifier()),
          exception
      );
    }
  }

  private void multi(final Stream<Document> documents) {
    BulkRequest bulkRequest = documents.reduce(
        new BulkRequest.Builder(),
        (builder, document) -> builder.operations(
            op -> op.index(
                i -> i.index(index)
                    .id(document.identifier())
                    .document(document.payload())
            )
        ),
        (current, next) -> current.operations(next.build().operations())
    ).refresh(Refresh.WaitFor).build();

    try {
      client.bulk(bulkRequest);
    } catch (IOException exception) {
      LOGGER.log(
          System.Logger.Level.ERROR,
          () -> "An unexpected issue occurred when indexing",
          exception
      );
    }
  }


}
