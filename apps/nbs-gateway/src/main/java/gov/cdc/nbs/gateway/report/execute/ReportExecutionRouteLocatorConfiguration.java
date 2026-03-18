package gov.cdc.nbs.gateway.report.execute;

import gov.cdc.nbs.gateway.modernization.ModernizationService;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.http.HttpMethod;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebSession;
import reactor.core.publisher.Mono;

/**
 * Configures the NBS Report Runner to route run and execute to the report-execution service. The
 * routes are only enabled when the {@code routes.report.execute.enabled} property is {@code true}
 * and any of the following criteria is satisfied.
 *
 * <ul>
 *   <li>Request is a POST
 *   <li>Path equal to {@code /nbs/nfc}
 *   <li>Request body has ObjectType="7"
 *   <li>Request body has OperationType="138" or "124"
 * </ul>
 */
@Configuration
@ConditionalOnProperty(
    prefix = "nbs.gateway.report.execution",
    name = "enabled",
    havingValue = "true")
class ReportExecutionRouteLocatorConfiguration {

  private static final String REPORT_OBJECT_TYPE = "7";
  private static final List<String> REPORT_OPERATION_TYPES = List.of("124", "138");

  @Bean
  RouteLocator reportExecuteRouteLocator(
      final RouteLocatorBuilder builder,
      @Qualifier("defaults") final List<GatewayFilter> defaults,
      final ModernizationService parameters) {
    return builder
        .routes()
        .route(
            "report-run-export-submit",
            route ->
                route
                    .order(Ordered.HIGHEST_PRECEDENCE)
                    .method(HttpMethod.POST)
                    .and()
                    .path("/nbs/nfc")
                    .and()
                    .readBody(LinkedMultiValueMap.class, bodyPredicate())
                    .filters(
                        filter ->
                            filter
                                .setPath("/nbs/redirect/report/execute")
                                .filters(defaults)
                                .filter(this::addSessionToBody))
                    .uri(parameters.uri()))
        .build();
  }

  @SuppressWarnings({"unchecked", "rawtypes"})
  private Predicate<LinkedMultiValueMap> bodyPredicate() {
    return r -> {
      LOGGER.log(System.Logger.Level.ERROR, () -> "body predicate called");
      return r.getFirst("ObjectType").equals(REPORT_OBJECT_TYPE)
          && REPORT_OPERATION_TYPES.contains(r.getFirst("OperationType"));
    };
  }

  private static final System.Logger LOGGER =
      System.getLogger(ReportExecutionRouteLocatorConfiguration.class.getName());

  private Mono<Void> addSessionToBody(
      final ServerWebExchange exchange, final GatewayFilterChain chain) {

    LOGGER.log(System.Logger.Level.ERROR, () -> "over here");

    ServerWebExchange newExchange =
        exchange
            .mutate()
            .request(
                builder ->
                    builder.headers(
                        headers -> {
                          exchange
                              .getSession()
                              .flatMap(
                                  (WebSession s) -> {
                                    Map<String, Object> attrs = s.getAttributes();
                                    LOGGER.log(
                                        System.Logger.Level.ERROR, () -> "got session attrs");
                                    attrs.forEach(
                                        (k, v) -> {
                                          LOGGER.log(
                                              System.Logger.Level.ERROR,
                                              () -> "header set: k: %s v: %s.".formatted(k, v));
                                          headers.set("test-session-state", v.toString());
                                        });

                                    return Mono.just(s);
                                  })
                              .toFuture();
                        }))
            .build();

    LOGGER.log(
        System.Logger.Level.ERROR,
        () ->
            "new header: %s"
                .formatted(newExchange.getRequest().getHeaders().getFirst("test-session-state")));
    return chain.filter(newExchange);
  }
}
