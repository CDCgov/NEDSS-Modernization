package gov.cdc.nbs.gateway.patient.file.events.investigation;

import gov.cdc.nbs.gateway.patient.file.PatientFileService;
import java.util.List;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;

/**
 * Configures the Patient Profile routes to the {@code /nbs/redirect/patient/file/events/return}
 * path of the {@code nbs.gateway.patient.profile.service} when the {@code
 * routes.patient.profile.enabled} property is {@code true} and the following criteria is satisfied;
 *
 * <ul>
 *   <li>Path equal to {@code /nbs/SelectCondition1.do}
 *   <li>Query Parameter {@code ContextAction} to {@code Cancel}
 * </ul>
 */
@Configuration
@ConditionalOnProperty(prefix = "nbs.gateway.patient.file", name = "enabled", havingValue = "true")
class CancelledInvestigationAddLocatorConfiguration {

  @Bean
  RouteLocator cancelledInvestigationAddPatientFileReturn(
      final RouteLocatorBuilder builder,
      @Qualifier("defaults") final List<GatewayFilter> defaults,
      final PatientFileService parameters) {
    return builder
        .routes()
        .route(
            "cancelled-investigation-add-patient-file-return",
            route ->
                route
                    .order(Ordered.HIGHEST_PRECEDENCE)
                    .path("/nbs/SelectCondition1.do")
                    .and()
                    .query("ContextAction", "Cancel")
                    .filters(
                        filter ->
                            filter
                                .setPath("/nbs/redirect/patient/file/events/return")
                                .filters(defaults))
                    .uri(parameters.uri()))
        .build();
  }
}
