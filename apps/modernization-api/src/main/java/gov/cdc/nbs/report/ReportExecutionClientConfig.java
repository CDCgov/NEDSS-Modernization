package gov.cdc.nbs.report;

import java.net.http.HttpClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.JdkClientHttpRequestFactory;
import org.springframework.web.client.RestClient;

@Configuration
public class ReportExecutionClientConfig {

  @Bean
  public RestClient reportExecutionClient(
      @Value("${nbs.report.execution.url}") final String url, RestClient.Builder builder) {
    HttpClient httpClient = HttpClient.newBuilder().version(HttpClient.Version.HTTP_1_1).build();
    JdkClientHttpRequestFactory jdkClientHttpRequestFactory =
        new JdkClientHttpRequestFactory(httpClient);
    return builder.baseUrl(url).requestFactory(jdkClientHttpRequestFactory).build();
  }
}
