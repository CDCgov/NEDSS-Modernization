package gov.cdc.nbs.security.oidc;

import gov.cdc.nbs.gateway.classic.NBSClassicService;
import gov.cdc.nbs.gateway.home.HomeService;
import java.net.URI;
import java.util.List;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Configuration
class NBS6LoginRouteLocatorConfiguration {

  @Bean
  RouteLocator nbs6LoginRouteLocator(
      final RouteLocatorBuilder builder,
      @Qualifier("defaults") final List<GatewayFilter> defaults,
      final NBSClassicService classic,
      final HomeService home) {
    return builder
        .routes()
        .route(
            "nbs6-login-bypass",
            route ->
                route
                    .path("/nbs/login")
                    .filters(filter -> filter.filter(this::login).filters(defaults))
                    .uri(classic.uri()))
        .route(
            "nbs6-block-nfc-based-login",
            route ->
                route
                    .order(Ordered.HIGHEST_PRECEDENCE)
                    .path("/nbs/nfc")
                    .and()
                    .query("UserName")
                    .filters(filters -> filters.redirect(302, home.base()))
                    .uri("no://op"))
        .build();
  }

  private Mono<Void> login(final ServerWebExchange exchange, final GatewayFilterChain chain) {
    return exchange
        .getPrincipal()
        .switchIfEmpty(Mono.defer(() -> redirectToOidcLogin(exchange)))
        .flatMap(
            principal -> {
              if (principal == null || principal instanceof AnonymousAuthenticationToken) {
                return redirectToOidcLogin(exchange);
              }

              return chain.filter(
                  exchange
                      .mutate()
                      .request(
                          request ->
                              request.path("/nbs/nfc?UserName=" + principal.getName()).build())
                      .build());
            });
  }

  private Mono<Void> redirectToOidcLogin(final ServerWebExchange exchange) {
    exchange.getResponse().setStatusCode(HttpStatus.FOUND);
    exchange.getResponse().getHeaders().setLocation(URI.create("/oauth2/authorization/nbs-users"));
    return exchange.getResponse().setComplete();
  }
}
