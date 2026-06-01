package gov.cdc.nbs.gateway.report;

import gov.cdc.nbs.gateway.RouteOrdering;
import gov.cdc.nbs.gateway.modernization.ModernizationService;
import java.util.List;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configures the NBS Report Runner to route run and execute to the modernized UI service. The
 * routes are only enabled when the {@code routes.report.execute.enabled} property is {@code true}
 * and any of the following criteria is satisfied.
 *
 * <ul>
 *   <li>Request is a GET
 *   <li>Path equal to {@code /nbs/ViewReport.do
 *   <li>Query param for data_source_uid
 *   <li>Query param for report_uid
 * </ul>
 */
@Configuration
@ConditionalOnProperty(
    prefix = "nbs.gateway.report.execution",
    name = "enabled",
    havingValue = "true")
class ReportAdminRouteLocatorConfiguration {

  private static final String DATA_SOURCE_UID = "data_source_uid";
  private static final String REPORT_UID = "report_uid";

  @Bean
  RouteLocator reportAdminRouteLocator(
      final RouteLocatorBuilder builder,
      @Qualifier("defaults") final List<GatewayFilter> defaults,
      final ModernizationService service) {
    return builder
        .routes()
        .route(
            "report-run",
            route ->
                route
                    .order(RouteOrdering.MODERNIZED_SERVICE.order())
                    .query(DATA_SOURCE_UID)
                    .and()
                    .query(REPORT_UID)
                    .and()
                    .path("/nbs/ViewReport.do")
                    .filters(
                        filter ->
                            filter
                                .setPath(
                                    "/report/management/configuration/{:report_uid}/{:data_source_uid}")
                                .filters(defaults))
                    .uri(service.uri()))
        .build();
  }
}
