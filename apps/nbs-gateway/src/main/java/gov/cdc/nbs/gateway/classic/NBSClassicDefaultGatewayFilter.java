package gov.cdc.nbs.gateway.classic;

import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.RemoveRequestHeaderGatewayFilterFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * This configuration provides {@link GatewayFilter} containing the necessary configuration to proxy
 * requests to the NBS Classic Service.
 */
@Configuration
class NBSClassicDefaultGatewayFilter {

  /** Provides a {@link GatewayFilter} that removes the {@code Referer} header from all requests. */
  @Bean("classic")
  GatewayFilter classicDefaultGatewayFilter() {
    return new RemoveRequestHeaderGatewayFilterFactory().apply(config -> config.setName("Referer"));
  }
}
