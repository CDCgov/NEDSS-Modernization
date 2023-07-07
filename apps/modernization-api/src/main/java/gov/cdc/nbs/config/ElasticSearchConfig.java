package gov.cdc.nbs.config;

import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.nio.client.HttpAsyncClientBuilder;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder.HttpClientConfigCallback;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.json.jackson.JacksonJsonpMapper;
import co.elastic.clients.transport.ElasticsearchTransport;
import co.elastic.clients.transport.rest_client.RestClientTransport;

@Configuration
public class ElasticSearchConfig {

    @Value("${nbs.elasticsearch.server:localhost}")
    private String elasticSearchServer;

    @Value("${nbs.elasticsearch.port:9200}")
    private Integer elasticSearchPort;

    @Bean
    public RestClient client() {
        final CredentialsProvider credentialsProvider = new BasicCredentialsProvider();
        credentialsProvider.setCredentials(AuthScope.ANY, new UsernamePasswordCredentials("elastic", "test321"));
        HttpHost host = new HttpHost(elasticSearchServer, elasticSearchPort);
        RestClient restClient = RestClient.builder(host)
                .setHttpClientConfigCallback(new HttpClientConfigCallback() {
                    @Override
                    public HttpAsyncClientBuilder customizeHttpClient(HttpAsyncClientBuilder httpClientBuilder) {
                        httpClientBuilder.disableAuthCaching();
                        return httpClientBuilder.setDefaultCredentialsProvider(credentialsProvider);
                    }
                }).build();
        return restClient;
    }

    @Bean
    public ElasticsearchClient elasticsearchTemplate(RestClient client, JacksonJsonpMapper mapper) {
        ElasticsearchTransport transport = new RestClientTransport(client, mapper);
        return new ElasticsearchClient(transport);
    }

}
