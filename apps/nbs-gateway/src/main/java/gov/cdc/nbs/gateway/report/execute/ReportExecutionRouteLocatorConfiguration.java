package gov.cdc.nbs.gateway.report.execute;

import java.util.List;
import java.util.function.Predicate;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.http.HttpMethod;
import org.springframework.util.LinkedMultiValueMap;

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
    prefix = "nbs.gateway.report.execute",
    name = "enabled",
    havingValue = "true")
class ReportExecutionRouteLocatorConfiguration {

  private static final System.Logger LOGGER =
      System.getLogger(ReportExecutionRouteLocatorConfiguration.class.getName());

  @Bean
  RouteLocator reportExecuteRouteLocator(
      final RouteLocatorBuilder builder,
      @Qualifier("defaults") final List<GatewayFilter> defaults,
      final ReportExecutionService parameters) {
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
                        filter -> filter.setPath("/nbs/redirect/report/execute").filters(defaults))
                    .uri(parameters.uri()))
        .build();
  }

  @SuppressWarnings({"unchecked", "rawtypes"})
  private Predicate<LinkedMultiValueMap> bodyPredicate() {
    return r -> {
      LOGGER.log(System.Logger.Level.ERROR, () -> "Body (%s)".formatted(r));

      return r.getFirst("ObjectType").equals("7")
          && List.of("124", "138").contains(r.getFirst("OperationType"));
    };
  }
}
