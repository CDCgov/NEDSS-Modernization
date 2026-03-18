package gov.cdc.nbs.gateway.classic.report;

import gov.cdc.nbs.gateway.RouteOrdering;
import gov.cdc.nbs.gateway.classic.NBSClassicService;
import java.util.List;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebSession;
import reactor.core.publisher.Mono;

@Configuration
class NBS6ReportCancelRouteLocatorConfiguration {

  /**
   * Creates a {@link RouteLocator} that clears the value of the {@code NBS-Report} when a request
   * is made to the report selection route {@code /nbs/ManageReport.do}.
   */
  @Bean
  RouteLocator nbs6ReportClearingRouteLocator(
      final RouteLocatorBuilder builder,
      @Qualifier("defaults") final List<GatewayFilter> defaults,
      final NBSClassicService classic) {
    return builder
        .routes()
        .route(
            "nbs6-report-cookie-clear",
            route ->
                route
                    .order(RouteOrdering.NBS_6.before())
                    .path("/nbs/ManageReports.do")
                    .and()
                    .cookie(NBSReportCookie.NAME, ".+")
                    .filters(
                        filter ->
                            filter
                                .filters(defaults)
                                .filter((exchange, chain) -> clearSession(exchange, chain))
                                .addResponseHeader(
                                    HttpHeaders.SET_COOKIE,
                                    NBSReportCookie.empty().toResponseCookie().toString()))
                    .uri(classic.uri()))
        .build();
  }

  private static final System.Logger LOGGER =
      System.getLogger(NBS6ReportCancelRouteLocatorConfiguration.class.getName());

  private Mono<Void> clearSession(
      final ServerWebExchange exchange, final GatewayFilterChain chain) {
    exchange
        .getSession()
        .flatMap(
            (WebSession s) -> {
              s.invalidate();

              return Mono.just(s);
            })
        .toFuture();
    return chain.filter(exchange);
  }
}
