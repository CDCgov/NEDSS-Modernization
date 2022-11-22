package gov.cdc.nbs.config;

import java.net.MalformedURLException;
import java.net.URL;

import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.fasterxml.jackson.databind.ObjectMapper;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.json.jackson.JacksonJsonpMapper;
import co.elastic.clients.transport.rest_client.RestClientTransport;

@Configuration
public class ElasticSearchConfig {

    @Value("${nbs.elasticsearch.url:http://localhost:9200}")
    private String elasticSearchUrl;

    @Bean
    public ElasticsearchClient client(ObjectMapper mapper) throws MalformedURLException {
        URL url = new URL(elasticSearchUrl);

        var restClient = RestClient.builder(new HttpHost(
                url.getHost(),
                url.getPort(),
                url.getProtocol()))
                .build();
        var transport = new RestClientTransport(restClient, new JacksonJsonpMapper(mapper));
        return new ElasticsearchClient(transport);
    }

}
