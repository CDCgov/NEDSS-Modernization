package gov.cdc.nbs.search.support;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import java.io.IOException;
import org.springframework.stereotype.Component;

@Component
public class ElasticsearchIndexCleaner {

  private final ElasticsearchClient client;

  public ElasticsearchIndexCleaner(final ElasticsearchClient client) {
    this.client = client;
  }

  public void clean(final String index) {
    try {
      client.deleteByQuery(
          delete ->
              delete
                  .index(index)
                  .query(query -> query.matchAll(all -> all))
                  .waitForCompletion(true)
                  .refresh(true));

    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }
}
