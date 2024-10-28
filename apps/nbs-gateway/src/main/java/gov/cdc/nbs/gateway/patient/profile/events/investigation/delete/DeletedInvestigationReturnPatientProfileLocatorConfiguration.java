package gov.cdc.nbs.gateway.patient.profile.events.investigation.delete;

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
 * Configures the Patient Profile routes to the {@code /nbs/redirect/patient/investigation/delete} path of the
 * {@code nbs.gateway.patient.profile.service} when the {@code routes.patient.profile.enabled} property is {@code true}
 * and the following criteria is satisfied;
 *
 * <ul>
 * <li>Path equal to {@code /nbs/PageAction.do}</li>
 * <li>Query Parameter {@code method} equal to {@code deleteSubmit}</li>
 * <li>Query Parameter {@code ContextAction} equal to
 * {@code ReturnToFileSummary}</li>
 * </ul>
 *
 * OR
 *
 * <ul>
 * <li>Path equal to {@code /nbs/PageAction.do}</li>
 * <li>Query Parameter {@code method} equal to {@code deleteSubmit}</li>
 * <li>Query Parameter {@code ContextAction} equal to {@code ReturnToFileEvents}</li>
 * </ul>
 *
 * OR
 *
 * <ul>
 * <li>Path equal to {@code /nbs/PageAction.do}</li>
 * <li>Query Parameter {@code method} equal to {@code deleteSubmit}</li>
 * <li>Query Parameter {@code ContextAction} equal to {@code FileSummary}</li>
 * </ul>
 */
@Configuration
@ConditionalOnProperty(prefix = "nbs.gateway.patient.profile", name = "enabled", havingValue = "true")
class DeletedInvestigationReturnPatientProfileLocatorConfiguration {

  @Bean
  RouteLocator deletedInvestigationPatientProfileReturn(
      final RouteLocatorBuilder builder,
      @Qualifier("defaults") final List<GatewayFilter> defaults,
      final PatientProfileService service
  ) {
    return builder.routes()
        .route(
            "deleted-investigation-patient-profile-return",
            route -> route.order(RouteOrdering.PATIENT_PROFILE.before())
                .path("/nbs/PageAction.do")
                .and()
                .query("method", "deleteSubmit")
                .and()
                .query("ContextAction", "ReturnToFileSummary|ReturnToFileEvents|FileSummary")
                .filters(
                    filter -> filter.setPath("/nbs/redirect/patient/investigation/delete")
                        .filters(defaults)
                )
                .uri(service.uri()))
        .build();
  }

}
