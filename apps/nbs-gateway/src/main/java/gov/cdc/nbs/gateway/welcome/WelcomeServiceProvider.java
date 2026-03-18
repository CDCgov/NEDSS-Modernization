package gov.cdc.nbs.gateway.welcome;

import gov.cdc.nbs.gateway.ui.UIService;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class WelcomeServiceProvider {
  @Bean
  @ConditionalOnProperty(prefix = "nbs.gateway.welcome", name = "enabled", havingValue = "false")
  RouteLocator welcomeRouteLocator(final RouteLocatorBuilder builder, final UIService uiService) {
    return builder
        .routes()
        .route(
            "welcome-service-redirect",
            route ->
                route
                    .path("/welcome/**")
                    .filters(filters -> filters.redirect(302, uiService.path("/")))
                    .uri("no://op"))
        .build();
  }
}
