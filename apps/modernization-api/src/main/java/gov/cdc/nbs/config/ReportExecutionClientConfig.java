package gov.cdc.nbs.config;

import java.net.http.HttpClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.JdkClientHttpRequestFactory;
import org.springframework.web.client.RestClient;

@Configuration
public class ReportExecutionClientConfig {

  @Bean
  public RestClient reportExecutionClient(RestClient.Builder builder) {
    HttpClient httpClient = HttpClient.newBuilder().version(HttpClient.Version.HTTP_1_1).build();
    JdkClientHttpRequestFactory jdkClientHttpRequestFactory =
        new JdkClientHttpRequestFactory(httpClient);
    return builder
        .baseUrl("http://report-execution:8001")
        .requestFactory(jdkClientHttpRequestFactory)
        .build();
  }
}
