package gov.cdc.nbs.gateway.classic.report;

import gov.cdc.nbs.gateway.RouteOrdering;
import gov.cdc.nbs.gateway.classic.NBSClassicService;
import java.util.List;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;

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
                                .addResponseHeader(
                                    HttpHeaders.SET_COOKIE,
                                    NBSReportCookie.empty().toResponseCookie().toString()))
                    .uri(classic.uri()))
        .build();
  }
}
