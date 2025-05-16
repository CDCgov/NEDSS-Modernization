package gov.cdc.nbs.gateway.deduplication;

import java.net.URI;
import java.net.URISyntaxException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConditionalOnExpression("${nbs.gateway.deduplication.enabled}")
public class DeduplicationServiceProvider {

  @Bean
  DeduplicationService deduplicationService(@Value("${nbs.gateway.deduplication.uri}") final String host)
      throws URISyntaxException {
    URI uri = new URI(host);
    return new DeduplicationService(uri);
  }
}
