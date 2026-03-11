package gov.cdc.nbs.gateway.pagebuilder;

import java.util.List;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;

/**
 * Configures the Page Builder API service routes so that any requests to {@code
 * /nbs/page-builder/api/**} are routed to the Page Builder Service.
 */
@Configuration
class PageBuilderAPIRouteLocatorConfiguration {

  @Bean
  RouteLocator pageBuilderAPIRouteLocator(
      final RouteLocatorBuilder builder,
      @Qualifier("defaults") final List<GatewayFilter> defaults,
      final PageBuilderService service) {
    return builder
        .routes()
        .route(
            "page-builder-api",
            route ->
                route
                    .order(Ordered.HIGHEST_PRECEDENCE)
                    .path("/nbs/page-builder/api/**")
                    .filters(filter -> filter.filters(defaults))
                    .uri(service.uri()))
        .build();
  }
}
