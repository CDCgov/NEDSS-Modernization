package gov.cdc.nbs.gateway.landing;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class LandingRouteLocatorConfiguration {

  @Bean
  RouteLocator landingRouteLocator(
      final RouteLocatorBuilder builder, final LandingService service) {
    return builder
        .routes()
        .route(
            "landing-service-redirect",
            route ->
                route
                    .path("/")
                    .and()
                    .not(landing -> landing.path(service.base()))
                    .filters(filters -> filters.redirect(302, service.base()))
                    .uri("no://op"))
        .build();
  }
}
