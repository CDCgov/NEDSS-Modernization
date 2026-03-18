package gov.cdc.nbs.gateway.classic.report;

import gov.cdc.nbs.gateway.RouteOrdering;
import gov.cdc.nbs.gateway.classic.NBSClassicService;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpCookie;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebSession;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Mono;

@Configuration
class NBS6ReportRunnerRouteLocatorConfiguration {

  /**
   * Creates a {@link RouteLocator} that uses the value of the {@code NBS-Report} cookie to set the
   * {@code Referer} header on the upstream request to the NBS6 {@code ReportWebProcessor}.
   */
  @Bean
  RouteLocator nbs6ReportRunnerRouteLocator(
      final RouteLocatorBuilder builder,
      @Qualifier("defaults") final List<GatewayFilter> defaults,
      final NBSClassicService classic) {
    return builder
        .routes()
        .route(
            "nbs6-report-runner",
            route ->
                route
                    .order(RouteOrdering.NBS_6.before())
                    .method(HttpMethod.POST)
                    .and()
                    .path("/nbs/nfc")
                    .and()
                    .cookie(NBSReportCookie.NAME, ".+")
                    .filters(
                        filter ->
                            filter
                                .filters(defaults)
                                .filter(
                                    (exchange, chain) -> resolveReport(classic, exchange, chain))
                                .filter(this::saveBodyToSession))
                    .uri(classic.uri()))
        .build();
  }

  private Mono<Void> resolveReport(
      final NBSClassicService classic,
      final ServerWebExchange exchange,
      final GatewayFilterChain chain) {

    HttpCookie cookie = exchange.getRequest().getCookies().getFirst(NBSReportCookie.NAME);

    ServerWebExchange serverWebExchange =
        (cookie != null) ? withReferer(classic, cookie, exchange) : exchange;

    return chain.filter(serverWebExchange);
  }

  private ServerWebExchange withReferer(
      final NBSClassicService classic, final HttpCookie cookie, final ServerWebExchange exchange) {

    String referer =
        UriComponentsBuilder.fromUri(classic.uri())
            .pathSegment("report", cookie.getValue())
            .toUriString();

    return exchange
        .mutate()
        .request(builder -> builder.headers(headers -> headers.set(HttpHeaders.REFERER, referer)))
        .build();
  }

  private static final System.Logger LOGGER =
      System.getLogger(NBS6ReportRunnerRouteLocatorConfiguration.class.getName());

  private Mono<Void> saveBodyToSession(
      final ServerWebExchange exchange, final GatewayFilterChain chain) {
    String body = exchange.getRequest().getBody().toString();
    LOGGER.log(System.Logger.Level.ERROR, () -> "here");

    String path = exchange.getRequest().getPath().toString();
    exchange
        .getSession()
        .flatMap(
            (WebSession s) -> {
              s.start();
              Map<String, Object> attrs = s.getAttributes();
              attrs.replace(path, body);
              LOGGER.log(
                  System.Logger.Level.ERROR, () -> "saved: k: %s v: %s.".formatted(path, body));

              s.save();
              return Mono.just(s);
            })
        .toFuture();
    return chain.filter(exchange);
  }
}
