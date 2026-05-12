package gov.cdc.nbs.search;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch._types.Refresh;
import co.elastic.clients.elasticsearch.core.BulkRequest;
import co.elastic.clients.util.MissingRequiredPropertyException;
import java.io.IOException;
import java.util.stream.Stream;
import org.springframework.stereotype.Component;

@Component
public class ElasticsearchSimpleDocumentIndexer {

  private static final System.Logger LOGGER =
      System.getLogger(ElasticsearchSimpleDocumentIndexer.class.getName());

  private final ElasticsearchClient client;

  public ElasticsearchSimpleDocumentIndexer(final ElasticsearchClient client) {
    this.client = client;
  }

  public void index(final String index, final SimpleDocument document) {

    try {
      client.index(
          indexing ->
              indexing
                  .index(index)
                  .id(document.identifier())
                  .document(document.payload())
                  .refresh(Refresh.True));
    } catch (IOException exception) {
      LOGGER.log(
          System.Logger.Level.ERROR,
          () -> "Unable to index %s (%s)".formatted(index, document.identifier()),
          exception);
    }
  }

  public void index(final String index, final Stream<SimpleDocument> documents) {
    try {
      BulkRequest bulkRequest =
          documents
              .reduce(
                  new BulkRequest.Builder(),
                  (builder, document) ->
                      builder.operations(
                          op ->
                              op.index(
                                  i ->
                                      i.index(index)
                                          .id(document.identifier())
                                          .document(document.payload()))),
                  (current, next) -> current.operations(next.build().operations()))
              .refresh(Refresh.WaitFor)
              .build();

      client.bulk(bulkRequest);
    } catch (MissingRequiredPropertyException exception) {
      LOGGER.log(
          System.Logger.Level.ERROR, () -> "Indexing %s skipped.".formatted(index), exception);
    } catch (IOException exception) {
      LOGGER.log(
          System.Logger.Level.ERROR,
          () -> "An unexpected issue occurred when indexing %s".formatted(index),
          exception);
    }
  }
}
