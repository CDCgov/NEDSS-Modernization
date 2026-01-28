package gov.cdc.nbs.search;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.json.jackson.JacksonJsonpMapper;
import co.elastic.clients.transport.ElasticsearchTransport;
import co.elastic.clients.transport.rest5_client.Rest5ClientTransport;
import co.elastic.clients.transport.rest5_client.low_level.Rest5Client;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.net.URI;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class ElasticsearchConfiguration {


  @Bean
  Rest5Client elasticsearchRestClient(
      @Value("${nbs.elasticsearch.url}") final String url) {
    return Rest5Client.builder(URI.create(url))
        .build();
  }

  @Bean
  ElasticsearchTransport elasticsearchTransport(
      final Rest5Client restClient,
      final ObjectMapper objectMapper) {
    return new Rest5ClientTransport(
        restClient,
        new JacksonJsonpMapper(objectMapper));
  }

  @Bean
  ElasticsearchClient elasticsearchClient(final ElasticsearchTransport transport) {
    return new ElasticsearchClient(transport);
  }
}
