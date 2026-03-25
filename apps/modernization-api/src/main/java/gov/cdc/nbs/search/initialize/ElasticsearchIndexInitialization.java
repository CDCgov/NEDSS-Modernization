package gov.cdc.nbs.search.initialize;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import gov.cdc.nbs.search.SimpleIndex;
import java.util.Collection;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class ElasticsearchIndexInitialization {

  @Bean
  InitializingBean ensureIndexes(
      final Collection<SimpleIndex> indices, final ElasticsearchClient client) {
    return () ->
        new ElasticsearchIndexEnsurer(
                new ElasticsearchIndexExistenceVerifier(client),
                new ElasticsearchIndexCreator(client))
            .ensure(indices);
  }
}
