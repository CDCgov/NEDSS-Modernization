package gov.cdc.nbs.gateway.report;

import com.fasterxml.jackson.databind.JsonNode;
import gov.cdc.nbs.gateway.modernization.ModernizationService;
import java.util.HashMap;
import java.util.List;
import java.util.function.Predicate;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.handler.AsyncPredicate;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.cloud.gateway.support.ServerWebExchangeUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Mono;

/**
 * Configures the NBS Report Runner to route run and execute to the modernized UI service. The
 * routes are only enabled when the {@code routes.report.execute.enabled} property is {@code true}
 * and any of the following criteria is satisfied.
 *
 * <ul>
 *   <li>Request is a POST
 *   <li>Path equal to {@code /nbs/nfc}
 *   <li>Request body has ObjectType="7"
 *   <li>Request body has OperationType="117"
 *   <li>The referenced report has been modernized
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

  private static final System.Logger LOGGER =
      System.getLogger(ReportExecutionRouteLocatorConfiguration.class.getName());

  @Bean
  RouteLocator reportExecuteRouteLocator(
      final RouteLocatorBuilder builder,
      @Qualifier("defaults") final List<GatewayFilter> defaults,
      final ModernizationService modService) {
    return builder
        .routes()
        .route(
            "report-run",
            route ->
                route
                    .order(Ordered.HIGHEST_PRECEDENCE)
                    .method(HttpMethod.POST)
                    .and()
                    .path("/nbs/nfc")
                    .and()
                    .readBody(LinkedMultiValueMap.class, bodyPredicate())
                    .and()
                    .asyncPredicate(isModPredicate(modService))
                    .filters(filter -> filter.filter(this::redirectToMod))
                    .uri("no://op"))
        .build();
  }

  /** Check the /nbs/nfc post matches the object and operation for running a report */
  @SuppressWarnings({"unchecked", "rawtypes"})
  private Predicate<LinkedMultiValueMap> bodyPredicate() {
    return body ->
        REPORT_OBJECT_TYPE.equals(body.getFirst("ObjectType"))
            && REPORT_OPERATION_TYPE.equals(body.getFirst("OperationType"));
  }

  /** Query the mod API to determine if this report should be run via NBS 7 or NBS 6 */
  private AsyncPredicate<ServerWebExchange> isModPredicate(final ModernizationService modService) {
    return exchange -> {
      String path =
          UriComponentsBuilder.fromPath("/nbs/api/report/configuration/{reportUid}/{dataSourceUid}")
              .uri(modService.uri())
              .build()
              .expand(getParamsFromBody(exchange))
              .toUriString();

      return WebClient.create()
          .get()
          .uri(path)
          .accept(MediaType.APPLICATION_JSON)
          .cookies(
              // Copy the cookies from the original request onto the new request to make sure auth
              // works
              newCookies ->
                  exchange
                      .getRequest()
                      .getCookies()
                      .forEach(
                          (name, cookies) ->
                              cookies.forEach(cookie -> newCookies.add(name, cookie.getValue()))))
          .retrieve()
          .bodyToMono(JsonNode.class)
          .doOnError(
              err ->
                  LOGGER.log(
                      System.Logger.Level.ERROR,
                      "Error querying modernization-api for report metadata: %s"
                          .formatted(err.getMessage())))
          .flatMap(
              b -> {
                JsonNode runnerNode = b.get("runner");
                if (runnerNode == null) return Mono.just(false);
                String runner = runnerNode.textValue();
                return Mono.just("python".equals(runner));
              });
    };
  }

  /**
   * Redirect this request to the NBS 7 report UI
   *
   * <p>This is modeled after the `.redirect` method implementation, but because of the reading the
   * params from the body and interpolating into the location, it couldn't be used directly
   */
  private Mono<Void> redirectToMod(
      final ServerWebExchange exchange, final GatewayFilterChain chain) {
    ServerWebExchangeUtils.setResponseStatus(exchange, HttpStatus.FOUND);

    HashMap<String, String> params = getParamsFromBody(exchange);
    String location =
        // Make sure the location matches the service's original request (the gateway) so the
        // cookies will be included on the new request to the location indicated hre
        UriComponentsBuilder.fromUri(exchange.getRequest().getURI())
            .replacePath("/report/{reportUid}/{dataSourceUid}/run")
            .build()
            .expand(params)
            .toUriString();

    ServerHttpResponse response = exchange.getResponse();
    response.getHeaders().set(HttpHeaders.LOCATION, location);
    return response.setComplete();
  }

  /**
   * Get the report UID and DataSource UID from the requests body and put in a hashmap.
   *
   * <p>MUST BE RUN AFTER `readBody` PREDICATE so the parsed body is cached
   */
  @SuppressWarnings({"unchecked"})
  private HashMap<String, String> getParamsFromBody(ServerWebExchange exchange) {
    // UPGRADE TRIPPING HAZARD: This key is a private part of the `ReadBodyRoutePredicateFactory`
    // implementation. It saves us needing to re-parse the cached body bytes into the map (and
    // deal with Mono's to do so...)
    LinkedMultiValueMap<String, String> body =
        (LinkedMultiValueMap<String, String>)
            exchange.getRequest().getAttributes().get("cachedRequestBodyObject");

    HashMap<String, String> params = new HashMap<>();
    if (body == null) {
      // This should never happen, but if it does will present weirdly, so leaving a breadcrumb
      LOGGER.log(System.Logger.Level.ERROR, "No request body found on report execution request");
      return params;
    }
    params.put("reportUid", body.getFirst("ReportUID"));
    params.put("dataSourceUid", body.getFirst("DataSourceUID"));
    return params;
  }
}
