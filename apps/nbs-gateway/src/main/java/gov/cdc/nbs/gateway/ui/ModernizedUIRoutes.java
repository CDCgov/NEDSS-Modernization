package gov.cdc.nbs.gateway.ui;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class ModernizedUIRoutes {

  @Bean
  RouteLocator goodbyeRouteLocator(
      final RouteLocatorBuilder builder,
      final UIService uiService) {
    return builder.routes()
        .route(
            "classic-logout-redirect",
            route -> route.path("/nbs/logOut")
                .filters(filters -> filters.redirect(302, uiService.path("/goodbye"))
                ).uri("no://op")
        )
        .build();
  }

  @Bean
  RouteLocator timeoutRouteLocator(
      final RouteLocatorBuilder builder,
      final UIService uiService) {
    return builder.routes()
        .route(
            "classic-timeout-redirect",
            route -> route.path("/nbs/timeout")
                .filters(
                    filters -> filters.redirect(302, uiService.path("/expired"))
                ).uri("no://op")
        )
        .build();
  }

}
