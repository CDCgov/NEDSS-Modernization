package gov.cdc.nbs.report;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.StreamReadConstraints;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.net.http.HttpClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.JdkClientHttpRequestFactory;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestClient;

@Configuration
public class ReportExecutionClientConfig {

  @Bean
  public RestClient reportExecutionClient(
      @Value("${nbs.report.execution.url}") final String url, @Value("${nbs.report.execution.max_size}") final Integer size, RestClient.Builder builder) {
    ObjectMapper largeResponseMapper =
        new ObjectMapper(
            JsonFactory.builder()
                .streamReadConstraints(
                    StreamReadConstraints.builder().maxStringLength(size).build())
                .build());

    // set to HttpClient to version 1.1, otherwise python report execution service has issue parsing
    // this request
    // https://github.com/spring-projects/spring-framework/issues/33275#issuecomment-2252184391
    HttpClient httpClient = HttpClient.newBuilder().version(HttpClient.Version.HTTP_1_1).build();
    JdkClientHttpRequestFactory jdkClientHttpRequestFactory =
        new JdkClientHttpRequestFactory(httpClient);
    return builder
        .baseUrl(url)
        .requestFactory(jdkClientHttpRequestFactory)
        .messageConverters(
            converters -> {
              converters.removeIf(
                  converter -> converter instanceof MappingJackson2HttpMessageConverter);

              converters.add(new MappingJackson2HttpMessageConverter(largeResponseMapper));
            })
        .build();
  }
}
