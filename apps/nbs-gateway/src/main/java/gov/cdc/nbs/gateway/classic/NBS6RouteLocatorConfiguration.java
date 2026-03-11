package gov.cdc.nbs.gateway.classic;

import gov.cdc.nbs.gateway.RouteOrdering;
import java.util.List;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class NBS6RouteLocatorConfiguration {

  @Bean
  RouteLocator nbs6RouteLocator(
      final RouteLocatorBuilder builder,
      @Qualifier("defaults") final List<GatewayFilter> defaults,
      @Qualifier("classic") final GatewayFilter classicFilter,
      final NBSClassicService service) {
    return builder
        .routes()
        .route(
            "nb6",
            route ->
                route
                    .order(RouteOrdering.NBS_6.order())
                    .path("/nbs/**")
                    .filters(filter -> filter.filters(defaults).filter(classicFilter))
                    .uri(service.uri()))
        .build();
  }
}
