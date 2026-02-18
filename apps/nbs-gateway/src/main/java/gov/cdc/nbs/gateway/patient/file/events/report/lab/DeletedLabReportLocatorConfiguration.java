package gov.cdc.nbs.gateway.patient.file.events.report.lab;

import gov.cdc.nbs.gateway.RouteOrdering;
import gov.cdc.nbs.gateway.patient.file.PatientFileService;
import java.util.List;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configures the Patient Profile routes to the {@code /nbs/redirect/patient/file/events/return}
 * path of the {@code nbs.gateway.patient.profile.service} when the {@code
 * routes.patient.profile.enabled} property is {@code true}.
 */
@Configuration
@ConditionalOnProperty(prefix = "nbs.gateway.patient.file", name = "enabled", havingValue = "true")
class DeletedLabReportLocatorConfiguration {

  /**
   * Redirects to the Modernized Patient Profile when the following criteria is satisfied;
   *
   * <ul>
   *   <li>Path equal to {@code /nbs/LoadViewFile1.do}
   *   <li>Query Parameter {@code ContextAction} equal to {@code Delete}
   * </ul>
   */
  @Bean
  RouteLocator deletedLabReportPatientProfileReturn(
      final RouteLocatorBuilder builder,
      @Qualifier("defaults") final List<GatewayFilter> defaults,
      final PatientFileService parameters) {
    return builder
        .routes()
        .route(
            "deleted-lab-report-patient-file-return",
            route ->
                route
                    .order(RouteOrdering.NBS_6.before())
                    .path("/nbs/LoadViewFile1.do")
                    .and()
                    .query("ContextAction", "Delete")
                    .filters(
                        filter ->
                            filter
                                .setPath("/nbs/redirect/patient/file/events/return")
                                .filters(defaults))
                    .uri(parameters.uri()))
        .build();
  }
}
