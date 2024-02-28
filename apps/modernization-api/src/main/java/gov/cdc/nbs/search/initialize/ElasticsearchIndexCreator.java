package gov.cdc.nbs.search.initialize;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import gov.cdc.nbs.search.SimpleIndex;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;

class ElasticsearchIndexCreator {

  private static final System.Logger LOGGER = System.getLogger(ElasticsearchIndexCreator.class.getName());

  private final ElasticsearchClient client;

  ElasticsearchIndexCreator(final ElasticsearchClient client) {
    this.client = client;
  }

  void create(final SimpleIndex index) throws IOException {
    LOGGER.log(
        System.Logger.Level.DEBUG,
        () -> String.format("Creating the index %s using the descriptor %s", index.name(), index.descriptor())
    );
    try (InputStream json = Files.newInputStream(index.descriptor())) {
      this.client.indices().create(
          create -> create.index(index.name()).withJson(json)
      );
    }
  }
}
