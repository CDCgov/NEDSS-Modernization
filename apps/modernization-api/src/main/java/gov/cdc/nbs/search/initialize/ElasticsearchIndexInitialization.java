package gov.cdc.nbs.search.initialize;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import gov.cdc.nbs.search.SimpleIndex;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Collection;


@Configuration
class ElasticsearchIndexInitialization {

  @Bean
  InitializingBean ensureIndexes(
      final Collection<SimpleIndex> indices,
      final ElasticsearchClient client
  ) {
    return () -> new ElasticsearchIndexEnsurer(
        new ElasticsearchIndexExistenceVerifier(client),
        new ElasticsearchIndexCreator(client)
    ).ensure(indices);
  }

}
