package gov.cdc.nbs.search;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch._types.Refresh;
import co.elastic.clients.elasticsearch.core.BulkRequest;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.stream.Stream;

@Component
public class ElasticsearchSimpleDocumentIndexer {

  private static final System.Logger LOGGER = System.getLogger(ElasticsearchSimpleDocumentIndexer.class.getName());


  private final ElasticsearchClient client;


  public ElasticsearchSimpleDocumentIndexer(final ElasticsearchClient client) {
    this.client = client;
  }

  public void index(final String index, final SimpleDocument document) {

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

  public void index(final String index, final Stream<SimpleDocument> documents) {
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
          () -> String.format("An unexpected issue occurred when indexing %s", index),
          exception
      );
    }
  }
}
