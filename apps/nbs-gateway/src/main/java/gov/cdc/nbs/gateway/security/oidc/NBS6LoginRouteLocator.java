package gov.cdc.nbs.gateway.security.oidc;

import gov.cdc.nbs.gateway.classic.NBSClassicService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Configuration
@ConditionalOnProperty(name = "nbs.security.oidc.enabled", havingValue = "true")
class NBS6LoginRouteLocator {

  @Bean
  RouteLocator nbs6LoginRouteLocator(
      final RouteLocatorBuilder builder,
      @Qualifier("default") final GatewayFilter globalFilter,
      final NBSClassicService service
  ) {
    return builder.routes()
        .route(
            "nbs6-login",
            route -> route.path("/nbs/login")
                .filters(
                    filter -> filter.filter(this::login)
                        .filter(globalFilter)
                ).uri(service.uri())
        )
        .build();
  }

  private Mono<Void> login(final ServerWebExchange exchange, final GatewayFilterChain chain) {
    return ReactiveSecurityContextHolder.getContext()
        .map(a -> a.getAuthentication().getName())
        .flatMap(
            user -> chain.filter(
                exchange.mutate().request(
                    request -> request.path("/nbs/nfc?UserName=" + user)
                        .build()
                ).build()
            )
        );
  }
}
