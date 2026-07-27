package gov.cdc.nbs.gateway.report;

import gov.cdc.nbs.gateway.RouteOrdering;
import gov.cdc.nbs.gateway.filter.QueryParamToPathGatewayFilterFactory;
import gov.cdc.nbs.gateway.filter.QueryParamToPathGatewayFilterFactory.Config;
import gov.cdc.nbs.gateway.ui.UIService;
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
 * <ul>
 *   <li>Request is a GET
 *   <li>Path equal to {@code /nbs/NewReport.do
 * </ul>
 */
@Configuration
@ConditionalOnProperty(
    prefix = "nbs.gateway.report.execution",
    name = "enabled",
    havingValue = "true")
class ReportAdminRouteLocatorConfiguration {

  @Bean
  RouteLocator reportAdminRouteLocator(
      final RouteLocatorBuilder builder,
      @Qualifier("defaults") final List<GatewayFilter> defaults,
      final UIService service) {
    return builder
        .routes()
        .route(
            "report-config-view",
            route ->
                route
                    .order(RouteOrdering.MODERNIZED_SERVICE.order())
                    .query("data_source_uid")
                    .and()
                    .query("report_uid")
                    .and()
                    .path("/nbs/ViewReport.do")
                    .filters(
                        filter ->
                            filter
                                .filter(
                                    new QueryParamToPathGatewayFilterFactory()
                                        .apply(
                                            new Config(
                                                List.of("report_uid", "data_source_uid"),
                                                "/nbs/redirect/report/management/configuration/{report_uid}/{data_source_uid}")))
                                .filters(defaults))
                    .uri(service.uri()))
        .route(
            "report-config-add",
            route ->
                route
                    .order(RouteOrdering.MODERNIZED_SERVICE.order())
                    .path("/nbs/NewReport.do")
                    .filters(
                        filter ->
                            filter
                                .setPath("/nbs/redirect/report/management/configuration/add")
                                .filters(defaults))
                    .uri(service.uri()))
        .build();
  }
}
