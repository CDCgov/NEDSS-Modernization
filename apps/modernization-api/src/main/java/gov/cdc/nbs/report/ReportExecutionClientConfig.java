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

  private static final System.Logger LOGGER =
      System.getLogger(ReportExecutionClientConfig.class.getName());

  @Bean
  public RestClient reportExecutionClient(
      @Value("${nbs.report.execution.url}") final String url,
      @Value("${nbs.report.execution.max_size}") final String rawSize,
      RestClient.Builder builder) {
    Integer size = Integer.MAX_VALUE;
    try {
      size = Integer.valueOf(rawSize);
    } catch (Exception e) {
      LOGGER.log(
          System.Logger.Level.WARNING,
          "Unable to parse `nbs.report.execution.max_size` (%s). Using Integer max value (%s) instead."
              .formatted(rawSize, size),
          e);
    }

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
              converters.removeIf(MappingJackson2HttpMessageConverter.class::isInstance);
              converters.add(new MappingJackson2HttpMessageConverter(largeResponseMapper));
            })
        .build();
  }
}
