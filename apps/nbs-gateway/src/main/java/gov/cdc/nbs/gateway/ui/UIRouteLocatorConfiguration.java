package gov.cdc.nbs.gateway.ui;

import java.util.List;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;

/** Configures the {@code /**} path to route to the Modernized User Interface service. */
@Configuration
class UIRouteLocatorConfiguration {

  @Bean
  RouteLocator uiRouteLocator(
      final RouteLocatorBuilder builder,
      @Qualifier("defaults") final List<GatewayFilter> defaults,
      final UIService service) {
    return builder
        .routes()
        .route(
            "nbs7-ui",
            route ->
                route
                    .order(Ordered.LOWEST_PRECEDENCE)
                    .path("/**")
                    .filters(filter -> filter.filters(defaults))
                    .uri(service.uri()))
        .build();
  }
}
