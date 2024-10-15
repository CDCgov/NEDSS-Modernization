package gov.cdc.nbs.gateway.logo;

import gov.cdc.nbs.gateway.classic.NBSClassicService;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configures the routing for the NBS Logo at {@code GET} requests to {@code /images/nedssLogo.jpg} to use the NBS6
 * service to resolve the logo.  This route becomes active if the property {@code nbs.gateway.logo.location} is not
 * present.
 */
@Configuration
@ConditionalOnProperty(prefix = "nbs.gateway.logo", name = "file", matchIfMissing = true)
class NBS6LogoRouteConfiguration {

  @Bean
  RouteLocator nbsLogoRouteLocator(
      final RouteLocatorBuilder builder,
      final NBSClassicService service,
      final LogoSettings settings
  ) {
    return builder.routes()
        .route(
            "nbs6-logo",
            route -> route.path(settings.path())
                .uri(service.uri())
        )
        .build();
  }

}
