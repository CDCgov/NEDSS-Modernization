package gov.cdc.nbs.search;

import co.elastic.clients.elasticsearch.ElasticsearchClient;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.util.Collection;


public class ElasticsearchIndexEnsurer {

  private static final System.Logger LOGGER = System.getLogger(ElasticsearchIndexEnsurer.class.getName());

  private final ElasticsearchClient client;

  public ElasticsearchIndexEnsurer(final ElasticsearchClient client) {
    this.client = client;
  }

  /**
   * Ensures that an Elasticsearch index exists for each entry, creating the index if it does not exist.
   *
   * @param indices The {@link SimpleIndex}  instances to ensure.
   * @see #ensure(SimpleIndex)
   */
  public void ensure(final Collection<SimpleIndex> indices) {
    for (SimpleIndex index : indices) {
      ensure(index);
    }
  }

  /**
   * Ensures that an Elasticsearch index with the given name exists.  If the index does not exist it will be created
   * using the content of the {@code descriptor}.
   *
   * @param index The {@link SimpleIndex} to ensure.
   */
  public void ensure(final SimpleIndex index) {

    try {

      boolean exists = exists(index.name());

      LOGGER.log(
          System.Logger.Level.DEBUG,
          () -> String.format("Ensuring that index %s exists. Result [%s]", index.name(), exists)
      );

      if (!exists) {
        LOGGER.log(
            System.Logger.Level.DEBUG,
            () -> String.format("Creating the index %s using the descriptor %s", index.name(), index.descriptor())
        );
        initialize(index);
      }
    } catch (IOException exception) {
      LOGGER.log(
          System.Logger.Level.ERROR,
          () -> String.format("An unexpected error occurred when ensuring the existence of the %s index", index),
          exception
      );
    }
  }

  private boolean exists(final String index) throws IOException {
    return client.indices()
        .exists(exists -> exists.index(index))
        .value();
  }

  private void initialize(final SimpleIndex index) throws IOException {
    try (InputStream json = Files.newInputStream(index.descriptor())) {
      this.client.indices().create(
          create -> create.index(index.name()).withJson(json)
      );
    }
  }
}
