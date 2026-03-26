package gov.cdc.nbs.search.initialize;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import gov.cdc.nbs.search.SimpleIndex;
import java.io.IOException;
import java.io.InputStream;

class ElasticsearchIndexCreator {

  private static final System.Logger LOGGER =
      System.getLogger(ElasticsearchIndexCreator.class.getName());

  private final ElasticsearchClient client;

  ElasticsearchIndexCreator(final ElasticsearchClient client) {
    this.client = client;
  }

  void create(final SimpleIndex index) throws IOException {
    LOGGER.log(
        System.Logger.Level.DEBUG,
        () ->
            "Creating the index %s using the descriptor located at %s"
                .formatted(index.name(), index.location()));
    try (InputStream json = getClass().getResourceAsStream(index.location())) {
      this.client.indices().create(create -> create.index(index.name()).withJson(json));
    }
  }
}
