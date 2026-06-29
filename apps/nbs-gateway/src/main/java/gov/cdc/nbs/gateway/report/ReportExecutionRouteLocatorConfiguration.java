package gov.cdc.nbs.gateway.report;

import gov.cdc.nbs.gateway.modernization.ModernizationService;
import java.util.HashMap;
import java.util.List;
import java.util.function.Predicate;
import org.springframework.beans.factory.ObjectProvider;
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
import org.springframework.security.oauth2.client.OAuth2AuthorizeRequest;
import org.springframework.security.oauth2.client.ReactiveOAuth2AuthorizedClientManager;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
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
  private static final String REPORT_MOD_RUNNER = "python";

  private static final System.Logger LOGGER =
      System.getLogger(ReportExecutionRouteLocatorConfiguration.class.getName());

  @Bean
  RouteLocator reportExecuteRouteLocator(
      final RouteLocatorBuilder builder,
      @Qualifier("defaults") final List<GatewayFilter> defaults,
      final ModernizationService modService,
      final ObjectProvider<ReactiveOAuth2AuthorizedClientManager> authorizedClientManagerProvider) {
    // Only present when the `oidc` profile is active and OAuth2 client/login is configured.
    // Null in environments running the legacy nbs_token/JSESSIONID auth path (e.g. local
    // docker-compose), in which case the cookie copy below is what carries auth.
    ReactiveOAuth2AuthorizedClientManager authorizedClientManager =
        authorizedClientManagerProvider.getIfAvailable();
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
                    .asyncPredicate(isModPredicate(modService, authorizedClientManager))
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
  private AsyncPredicate<ServerWebExchange> isModPredicate(
      final ModernizationService modService,
      final ReactiveOAuth2AuthorizedClientManager authorizedClientManager) {
    return exchange -> {
      String path =
          UriComponentsBuilder.fromPath("/nbs/api/report/runner/{reportUid}/{dataSourceUid}")
              .uri(modService.uri())
              .build()
              .expand(getParamsFromBody(exchange))
              .toUriString();

      return resolveBearerToken(exchange, authorizedClientManager)
          .defaultIfEmpty("")
          .flatMap(
              bearerToken -> {
                WebClient.RequestHeadersSpec<?> request =
                    WebClient.create()
                        .get()
                        .uri(path)
                        .accept(MediaType.APPLICATION_JSON)
                        .cookies(
                            // Copy the cookies from the original request onto the new request to
                            // make sure auth works for the legacy nbs_token/JSESSIONID path
                            newCookies ->
                                exchange
                                    .getRequest()
                                    .getCookies()
                                    .forEach(
                                        (name, cookies) ->
                                            cookies.forEach(
                                                cookie -> newCookies.add(name, cookie.getValue()))));

                if (!bearerToken.isEmpty()) {
                  // Relay the gateway's own OAuth2 access token, mirroring what
                  // TokenRelayGatewayFilterFactory does for routes that go through the
                  // `defaults` filter chain. Required when the `oidc` profile is active, since
                  // modernization-api then authenticates via Bearer JWT, not cookies.
                  request = request.header(HttpHeaders.AUTHORIZATION, "Bearer " + bearerToken);
                }

                return request
                    .exchangeToMono(
                        response -> {
                          // If the api response has any set-cookie's, pass them on to overall
                          // response so they are set in the browser. This is important for auth.
                          exchange.getResponse().getCookies().addAll(response.cookies());

                          return response
                              .bodyToMono(String.class)
                              .flatMap(runner -> Mono.just(REPORT_MOD_RUNNER.equals(runner)));
                        })
                    .doOnError(
                        err ->
                            LOGGER.log(
                                System.Logger.Level.ERROR,
                                "Error querying modernization-api for report metadata: %s"
                                    .formatted(err.getMessage())));
              });
    };
  }

  /**
   * Resolve the current request's OAuth2 access token, if the `oidc` profile is active and the
   * user is authenticated via OAuth2 login. Returns an empty {@code Mono} (not an error) when
   * OIDC isn't configured, the user isn't an OAuth2 principal, or no authorized client can be
   * resolved - callers should fall back to cookie-based auth in that case.
   */
  private Mono<String> resolveBearerToken(
      final ServerWebExchange exchange,
      final ReactiveOAuth2AuthorizedClientManager authorizedClientManager) {
    if (authorizedClientManager == null) {
      return Mono.empty();
    }

    return exchange
        .getPrincipal()
        .filter(OAuth2AuthenticationToken.class::isInstance)
        .cast(OAuth2AuthenticationToken.class)
        .flatMap(
            authentication -> {
              OAuth2AuthorizeRequest authorizeRequest =
                  OAuth2AuthorizeRequest.withClientRegistrationId(
                          authentication.getAuthorizedClientRegistrationId())
                      .principal(authentication)
                      .attribute(ServerWebExchange.class.getName(), exchange)
                      .build();
              return authorizedClientManager.authorize(authorizeRequest);
            })
        .map(client -> client.getAccessToken().getTokenValue())
        .onErrorResume(
            err -> {
              LOGGER.log(
                  System.Logger.Level.WARNING,
                  "Failed to resolve OAuth2 access token for report execution token relay: %s"
                      .formatted(err.getMessage()));
              return Mono.empty();
            });
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
