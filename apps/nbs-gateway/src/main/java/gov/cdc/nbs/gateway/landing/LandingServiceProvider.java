package gov.cdc.nbs.gateway.landing;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import java.net.URI;
import java.net.URISyntaxException;

@Configuration
class LandingServiceProvider {

  @Bean
  LandingService landingService(
      @Value("${nbs.gateway.landing.uri}") final String host,
      @Value("${nbs.gateway.landing.base}") final String base) throws URISyntaxException {
    URI uri = new URI(host);

    return new LandingService(uri, base);
  }

  @Bean
  RouteLocator landingRouteLocator(
      final RouteLocatorBuilder builder,
      final LandingService landingService) {
    return builder.routes()
        .route(
            "landing-service-redirect", route -> route.path("/")
                .filters(filters -> filters.redirect(302, landingService.base())).uri("no://op"))
        .build();
  }
}
