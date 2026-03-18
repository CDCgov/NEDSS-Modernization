package gov.cdc.nbs.gateway.patient.file.events.investigation.delete;

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
 * Configures the Patient File routes to the {@code /nbs/redirect/patient/investigation/delete} path
 * of the {@code nbs.gateway.patient.file.service} when the {@code routes.patient.file.enabled}
 * property is {@code true} and the following criteria is satisfied;
 *
 * <ul>
 *   <li>Path equal to {@code /nbs/PageAction.do}
 *   <li>Query Parameter {@code method} equal to {@code deleteSubmit}
 *   <li>Query Parameter {@code ContextAction} equal to {@code ReturnToFileSummary}
 * </ul>
 *
 * OR
 *
 * <ul>
 *   <li>Path equal to {@code /nbs/PageAction.do}
 *   <li>Query Parameter {@code method} equal to {@code deleteSubmit}
 *   <li>Query Parameter {@code ContextAction} equal to {@code ReturnToFileEvents}
 * </ul>
 *
 * OR
 *
 * <ul>
 *   <li>Path equal to {@code /nbs/PageAction.do}
 *   <li>Query Parameter {@code method} equal to {@code deleteSubmit}
 *   <li>Query Parameter {@code ContextAction} equal to {@code FileSummary}
 * </ul>
 */
@Configuration
@ConditionalOnProperty(prefix = "nbs.gateway.patient.file", name = "enabled", havingValue = "true")
class DeletedInvestigationReturnPatientFileLocatorConfiguration {

  @Bean
  RouteLocator deletedInvestigationPatientFileReturn(
      final RouteLocatorBuilder builder,
      @Qualifier("defaults") final List<GatewayFilter> defaults,
      final PatientFileService service) {
    return builder
        .routes()
        .route(
            "deleted-investigation-patient-file-return",
            route ->
                route
                    .order(RouteOrdering.PATIENT_FILE.before())
                    .path("/nbs/PageAction.do")
                    .and()
                    .query("method", "deleteSubmit")
                    .and()
                    .query("ContextAction", "ReturnToFileSummary|ReturnToFileEvents|FileSummary")
                    .filters(
                        filter ->
                            filter
                                .setPath("/nbs/redirect/patient/investigation/delete")
                                .filters(defaults))
                    .uri(service.uri()))
        .build();
  }
}
