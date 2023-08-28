package gov.cdc.nbs.gateway.patient.profile;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configures the Patient Profile routes to the {@code /nbs/redirect/patientProfile/summary/return} path of the
 * {@code nbs.gateway.patient.profile.service} when the {@code routes.patient.profile.enabled} property is {@code true}
 * and the following criteria is satisfied;
 *
 * <ul>
 *     <li>Query Parameter {@code ContextAction} of {@code ReturnToFileSummary}</li>
 * </ul>
 */
@Configuration
@ConditionalOnProperty(prefix = "nbs.gateway.patient.profile", name = "enabled", havingValue = "true")
class PatientProfileSummaryReturnRouteLocatorConfiguration {

    @Bean
    RouteLocator patientProfileSummaryReturnLocator(
        final RouteLocatorBuilder builder,
        @Qualifier("default") final GatewayFilter globalFilter,
        final PatientProfileService parameters
    ) {
        return builder.routes()
            .route(
                "patient-profile-summary-return",
                route -> route.query("ContextAction", "ReturnToFileSummary")
                    .filters(
                        filter -> filter.setPath("/nbs/redirect/patientProfile/summary/return")
                            .filter(globalFilter)
                    )
                    .uri(parameters.uri())
            )
            .build();
    }
}
