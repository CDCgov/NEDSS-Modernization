package gov.cdc.nbs.gateway.patient.profile.events.report.lab;

import gov.cdc.nbs.gateway.RouteOrdering;
import gov.cdc.nbs.gateway.patient.profile.PatientProfileService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

/**
 * Configures the Patient Profile routes to the {@code /nbs/redirect/patientProfile/events/return} path of the
 * {@code nbs.gateway.patient.profile.service} when the {@code routes.patient.profile.enabled} property is
 * {@code true}.
 */
@Configuration
@ConditionalOnProperty(prefix = "nbs.gateway.patient.profile", name = "enabled", havingValue = "true")
class DeletedLabReportLocatorConfiguration {

  /**
   * Redirects to the Modernized Patient Profile when the following criteria is satisfied;
   *
   * <ul>
   * <li>Path equal to {@code /nbs/LoadViewFile1.do}</li>
   * <li>Query Parameter {@code ContextAction} equal to {@code Delete}</li>
   * </ul>
   */
  @Bean
  RouteLocator deletedLabReportPatientProfileReturn(
      final RouteLocatorBuilder builder,
      @Qualifier("defaults") final List<GatewayFilter> defaults,
      final PatientProfileService parameters
  ) {
    return builder.routes()
        .route(
            "deleted-lab-report-patient-profile-return",
            route -> route.order(RouteOrdering.NBS_6.before())
                .path("/nbs/LoadViewFile1.do")
                .and()
                .query("ContextAction", "Delete")
                .filters(
                    filter -> filter.setPath(
                            "/nbs/redirect/patientProfile/events/return")
                        .filters(defaults))
                .uri(parameters.uri())
        )
        .build();
  }
}
