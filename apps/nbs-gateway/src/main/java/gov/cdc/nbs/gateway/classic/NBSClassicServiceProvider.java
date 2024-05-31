package gov.cdc.nbs.gateway.classic;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import gov.cdc.nbs.gateway.ui.UIService;
import java.net.URI;
import java.net.URISyntaxException;

@Configuration
class NBSClassicServiceProvider {

  @Bean
  NBSClassicService nbsClassicService(
      @Value("${nbs.gateway.classic}") final String classic) throws URISyntaxException {
    URI uri = new URI(classic);
    return new NBSClassicService(uri);
  }

  @Bean
  RouteLocator logoutRouteLocator(
      final RouteLocatorBuilder builder,
      final UIService uiService) {
    return builder.routes()
        .route(
            "classic-logout-redirect", route -> route.path("/nbs/logOut")
                .filters(filters -> filters.redirect(302, uiService.path("/goodbye"))).uri("no://op"))
        .build();
  }

  @Bean
  RouteLocator timeoutRouteLocator(
      final RouteLocatorBuilder builder,
      final UIService uiService) {
    return builder.routes()
        .route(
            "classic-timeout-redirect", route -> route.path("/nbs/timeout")
                .filters(filters -> filters.redirect(302, uiService.path("/expired"))).uri("no://op"))
        .build();
  }
}
