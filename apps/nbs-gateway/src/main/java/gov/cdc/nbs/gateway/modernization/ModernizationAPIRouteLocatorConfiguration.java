package gov.cdc.nbs.gateway.modernization;

import gov.cdc.nbs.gateway.RouteOrdering;
import java.util.List;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;

/**
 * Configures the Modernized API service routes.
 *
 * <ul>
 *   <li>{@code /nbs/api/**}
 *   <li>{@code /encryption/**}
 * </ul>
 *
 * {@code POST} requests to {@code /graphql}
 */
@Configuration
class ModernizationAPIRouteLocatorConfiguration {

  @Bean
  RouteLocator modernizationAPIRouteLocator(
      final RouteLocatorBuilder builder,
      @Qualifier("defaults") final List<GatewayFilter> defaults,
      final ModernizationService service) {
    return builder
        .routes()
        .route(
            "modernization-api",
            route ->
                route
                    .order(RouteOrdering.MODERNIZATION_API.order())
                    .path("/nbs/api/**", "/encryption/**")
                    .filters(filter -> filter.filters(defaults))
                    .uri(service.uri()))
        .route(
            "graphql",
            route ->
                route
                    .method(HttpMethod.POST)
                    .and()
                    .path("/graphql")
                    .filters(filter -> filter.filters(defaults))
                    .uri(service.uri()))
        .build();
  }
}
