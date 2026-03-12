package gov.cdc.nbs.search.initialize;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch.cat.indices.IndicesRecord;
import gov.cdc.nbs.search.SimpleIndex;
import java.io.IOException;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

class ElasticsearchIndexExistenceVerifier {

  private static final System.Logger LOGGER =
      System.getLogger(ElasticsearchIndexExistenceVerifier.class.getName());

  private final ElasticsearchClient client;

  ElasticsearchIndexExistenceVerifier(final ElasticsearchClient client) {
    this.client = client;
  }

  /**
   * Returns a {@link Stream} containing any {@code SimpleIndex} instances representing indexes that
   * do not exist.
   *
   * @param indices The {@link SimpleIndex} instances to check for existence
   * @return The {@code SimpleIndex} instances that do not have
   */
  Collection<SimpleIndex> notExists(final Collection<SimpleIndex> indices) {

    Map<String, SimpleIndex> remaining =
        indices.stream().collect(Collectors.toMap(SimpleIndex::name, Function.identity()));

    try {

      List<IndicesRecord> existing = client.cat().indices().valueBody();

      for (IndicesRecord found : existing) {
        remaining.remove(found.index());
      }

      return remaining.values();
    } catch (IOException exception) {
      LOGGER.log(
          System.Logger.Level.ERROR,
          () -> "An unexpected error occurred when gathering the existing indices.",
          exception);
      return Collections.emptyList();
    }
  }
}
