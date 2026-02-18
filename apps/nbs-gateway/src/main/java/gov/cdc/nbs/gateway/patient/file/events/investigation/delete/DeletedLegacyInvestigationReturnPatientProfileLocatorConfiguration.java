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
 * of the {@code nbs.gateway.patient.profile.service} when the {@code
 * routes.patient.profile.enabled} property is {@code true} and the following criteria is satisfied;
 *
 * <ul>
 *   <li>Path equal to {@code /nbs/ViewInvestigation1.do}
 *   <li>Query Parameter {@code delete} equal to {@code true}
 *   <li>Query Parameter {@code ContextAction} equal to {@code ReturnToFileSummary}
 * </ul>
 *
 * OR
 *
 * <ul>
 *   <li>Path equal to {@code /nbs/ViewInvestigation3.do}
 *   <li>Query Parameter {@code delete} equal to {@code true}
 *   <li>Query Parameter {@code ContextAction} equal to {@code ReturnToFileEvents}
 * </ul>
 *
 * OR
 *
 * <ul>
 *   <li>Path equal to {@code /nbs/ViewInvestigation3.do}
 *   <li>Query Parameter {@code delete} equal to {@code true}
 *   <li>Query Parameter {@code ContextAction} equal to {@code FileSummary}
 * </ul>
 */
@Configuration
@ConditionalOnProperty(prefix = "nbs.gateway.patient.file", name = "enabled", havingValue = "true")
class DeletedLegacyInvestigationReturnPatientProfileLocatorConfiguration {

  @Bean
  RouteLocator deletedLegacyInvestigationPatientProfileReturn(
      final RouteLocatorBuilder builder,
      @Qualifier("defaults") final List<GatewayFilter> defaults,
      final PatientFileService service) {
    return builder
        .routes()
        .route(
            "deleted-legacy-investigation-patient-file-return",
            route ->
                route
                    .order(RouteOrdering.PATIENT_FILE.before())
                    .path(
                        "/nbs/ViewInvestigation1.do",
                        "/nbs/ViewInvestigation2.do",
                        "/nbs/ViewInvestigation3.do")
                    .and()
                    .query("delete", "true")
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
