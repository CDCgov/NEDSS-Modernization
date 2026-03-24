package gov.cdc.nbs.gateway.report;

import gov.cdc.nbs.gateway.ui.UIService;
import java.util.HashMap;
import java.util.List;
import java.util.function.Predicate;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.cloud.gateway.support.ServerWebExchangeUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.util.UriComponentsBuilder;
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
  private static final String REPORT_OPERATION_TYPE = "117";

  @Bean
  RouteLocator reportExecuteRouteLocator(
      final RouteLocatorBuilder builder,
      @Qualifier("defaults") final List<GatewayFilter> defaults,
      final UIService uiService) {
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
                                .filter(this::redirectToMod)
                                .redirect(
                                    302, uiService.path("/report/{reportUid}/{dataSourceUid}/run"))
                                .filters(defaults))
                    .uri("no://op"))
        .build();
  }

  @SuppressWarnings({"unchecked"})
  private Mono<Void> redirectToMod(
      final ServerWebExchange exchange, final GatewayFilterChain chain) {
    LinkedMultiValueMap<String, String> body =
        (LinkedMultiValueMap<String, String>)
            exchange.getRequest().getAttributes().get("cachedRequestBodyObject");

    HashMap<String, String> params = new HashMap<>();
    params.put("reportUid", body.getFirst("ReportUID"));
    params.put("dataSourceUid", body.getFirst("DataSourceUID"));

    ServerWebExchangeUtils.setResponseStatus(exchange, HttpStatus.FOUND);

    String location =
        UriComponentsBuilder.fromPath("/report/{reportUid}/{dataSourceUid}/run")
            .build()
            .expand(params)
            .getPath();

    ServerHttpResponse response = exchange.getResponse();
    response.getHeaders().set(HttpHeaders.LOCATION, location);
    return response.setComplete();
  }

  @SuppressWarnings({"unchecked", "rawtypes"})
  private Predicate<LinkedMultiValueMap> bodyPredicate() {
    return r ->
        REPORT_OBJECT_TYPE.equals(r.getFirst("ObjectType"))
            && REPORT_OPERATION_TYPE.equals(r.getFirst("OperationType"));
  }
}
