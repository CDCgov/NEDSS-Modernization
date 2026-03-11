package gov.cdc.nbs.search;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.json.jackson.JacksonJsonpMapper;
import co.elastic.clients.transport.ElasticsearchTransport;
import co.elastic.clients.transport.rest_client.RestClientTransport;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class ElasticsearchConfiguration {

  @Bean
  RestClient elasticsearchRestClient(@Value("${nbs.elasticsearch.url}") final String url) {
    return RestClient.builder(HttpHost.create(url)).build();
  }

  @Bean
  ElasticsearchTransport elasticsearchTransport(
      final RestClient restClient, final ObjectMapper objectMapper) {
    return new RestClientTransport(restClient, new JacksonJsonpMapper(objectMapper));
  }

  @Bean
  ElasticsearchClient elasticsearchClient(final ElasticsearchTransport transport) {
    return new ElasticsearchClient(transport);
  }
}
