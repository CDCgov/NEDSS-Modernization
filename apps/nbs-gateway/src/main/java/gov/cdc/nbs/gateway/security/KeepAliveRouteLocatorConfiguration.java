package gov.cdc.nbs.gateway.security;

import gov.cdc.nbs.gateway.classic.NBSClassicService;
import java.util.List;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class KeepAliveRouteLocatorConfiguration {

  @Bean
  RouteLocator keepAlive6RouteLocator(
      final RouteLocatorBuilder builder,
      @Qualifier("defaults") final List<GatewayFilter> defaults,
      @Qualifier("classic") final GatewayFilter classicFilter,
      final NBSClassicService service) {
    return builder
        .routes()
        .route(
            "keep-alive",
            route ->
                route
                    .path("/keep-alive")
                    .filters(
                        filter ->
                            filter
                                .filters(defaults)
                                .filter(classicFilter)
                                .setPath("/nbs/HomePage.do")
                                .addRequestParameter("method", "loadHomePage"))
                    .uri(service.uri()))
        .build();
  }
}
