package gov.cdc.nbs.search;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch._types.Refresh;
import co.elastic.clients.elasticsearch.core.BulkRequest;
import co.elastic.clients.util.MissingRequiredPropertyException;
import java.io.IOException;
import java.util.stream.Stream;
import org.springframework.stereotype.Component;

/**
 * Indexes {@link SimpleDocument} instances into Elasticsearch.
 *
 * <p>The document {@linkplain SimpleDocument#identifier() identifier} is used as the Elasticsearch
 * document id, and the document {@linkplain SimpleDocument#payload() payload} is written as the
 * indexed source. Indexing failures are logged and are not propagated to callers.
 */
@Component
public class ElasticsearchSimpleDocumentIndexer {

  private static final System.Logger LOGGER =
      System.getLogger(ElasticsearchSimpleDocumentIndexer.class.getName());

  private final ElasticsearchClient client;

  /**
   * Creates an indexer that writes documents through the provided Elasticsearch client.
   *
   * @param client the Elasticsearch client used for single-document and bulk indexing requests
   */
  public ElasticsearchSimpleDocumentIndexer(final ElasticsearchClient client) {
    this.client = client;
  }

  /**
   * Indexes a single document into the given index.
   *
   * <p>The request refreshes the index immediately after the document is written.
   *
   * @param index the Elasticsearch index name
   * @param document the document to index
   */
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

  /**
   * Indexes a stream of documents into the given index using a bulk request.
   *
   * <p>The request waits for the indexed documents to become visible before returning. The provided
   * stream is consumed while building the bulk request.
   *
   * @param index the Elasticsearch index name
   * @param documents the documents to index
   */
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
