package gov.cdc.nbs.gateway.deduplication;

import java.util.List;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;

@Configuration
@ConditionalOnExpression("${nbs.gateway.deduplication.api.enabled}")
class DeduplicationAPIRouteLocatorConfiguration {

  @Bean
  RouteLocator deduplicationAPIRouteLocator(
      final RouteLocatorBuilder builder,
      @Qualifier("defaults") final List<GatewayFilter> defaults,
      final DeduplicationService service) {
    return builder.routes()
        .route(
            "deduplication-api",
            route -> route.order(Ordered.HIGHEST_PRECEDENCE)
                .path("/nbs/api/deduplication/**")
                .filters(filter -> filter.filters(defaults))
                .uri(service.uri()))
        .build();
  }
}
