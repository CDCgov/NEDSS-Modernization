package gov.cdc.nbs.gateway.security.oidc;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.net.URI;

@Configuration
@ConditionalOnProperty(name = "nbs.security.oidc.enabled", havingValue = "true")
class NBS6LogoutRouteLocatorConfiguration {

  @Bean
  RouteLocator nbs6LogoutRouteLocator(
      final RouteLocatorBuilder builder,
      final AuthenticationService service
  ) {
    URI logout = service.uri().resolve(service.path("/protocol/openid-connect/logout"));
    return builder.routes()
        .route(
            "nbs6-logout",
            route -> route.path("/nbs/logout")
                .filters(filters -> filters.redirect(302, logout))
                .uri(service.uri())
        )
        .build();
  }

}
