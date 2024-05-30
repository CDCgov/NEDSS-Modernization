package gov.cdc.nbs.gateway.classic.report;

import gov.cdc.nbs.gateway.classic.NBSClassicService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.cloud.gateway.support.ServerWebExchangeUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Map;

/**
 * The NBS-Gateway uses the {@code Referrer-Policy} of {@code no-referrer} which signals to browsers to not apply the
 * {@code Referer} HTTP header however, the NBS6 ReportWebProcessor uses the Referer HTTP header to resolve which report
 * to run.  To ensure that a report can be run the {@code NBS-Report} cookie has been introduced to store the report
 * name.  When a POST request is made to the {@code /nbs/nfc} route the {@code NBS-Report} is then used to apply the
 * {@code Referer} header expected by the NBS6 ReportWebProcessor.
 */
@Configuration
class NBS6ReportPageRouteLocatorConfiguration {

  /**
   * Creates a {@link RouteLocator} that adds a cookie to the response of any {@code /nbs/report} request with the name
   * of the report chosen.
   */
  @Bean
  RouteLocator nbs6ReportPageRouteLocator(
      final RouteLocatorBuilder builder,
      @Qualifier("defaults") final List<GatewayFilter> defaults,
      final NBSClassicService classic
  ) {
    return builder.routes()
        .route(
            "nbs6-report-cookie-applier",
            route -> route.path("/nbs/report/{page:reports|basic|advanced|column|run|save|error}")
                .filters(
                    filter -> filter.filters(defaults)
                        .filter(this::applyReportCookie)
                )
                .uri(classic.uri())
        )
        .build();
  }

  private Mono<Void> applyReportCookie(
      final ServerWebExchange exchange,
      final GatewayFilterChain chain
  ) {

    Map<String, String> uriVariables = ServerWebExchangeUtils.getUriTemplateVariables(exchange);

    String segment = uriVariables.get("page");

    return chain.filter(exchange).then(
        Mono.fromRunnable(
            () -> exchange.getResponse().addCookie(
                new NBSReportCookie(segment).toResponseCookie()
            )
        )
    );
  }
}
