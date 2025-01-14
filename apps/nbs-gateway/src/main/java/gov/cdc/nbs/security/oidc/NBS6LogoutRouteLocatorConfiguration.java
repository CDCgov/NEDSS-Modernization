package gov.cdc.nbs.security.oidc;

import gov.cdc.nbs.gateway.classic.NBSClassicService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
class NBS6LogoutRouteLocatorConfiguration {

  @Bean
  RouteLocator loggedOut(
      final RouteLocatorBuilder builder,
      @Qualifier("defaults") final List<GatewayFilter> defaults,
      final NBSClassicService service) {
    return builder.routes()
        .route(
            "logged-out",
            route -> route.path("/nbs/logged-out")
                .filters(
                    filters -> filters
                        .setPath("/nbs/logout")
                        .filters(defaults))
                .uri(service.uri()))
        .build();
  }

}
