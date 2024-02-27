package gov.cdc.nbs.search;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
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
    return () -> new ElasticsearchIndexEnsurer(client).ensure(indices);
  }

}
