package gov.cdc.nbs.gateway.patient.profile;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;

/**
 * Configures the Patient Profile routes with a ContextAction of {@code ViewFile} or {@code FileSummary} to the
 * {@code nbs.gateway.patient.profile.service}.  The routes are only enabled when the
 * {@code routes.patient.profile.enabled} property is {@code true} and any of the following criteria is satisfied;
 *
 * <ul>
 *     <li>Query Parameter {@code ContextAction} of {@code ViewFile}</li>
 *     <li>Query Parameter {@code ContextAction} of {@code FileSummary}</li>
 * </ul>
 */
@Configuration
@ConditionalOnProperty(prefix = "nbs.gateway.patient.profile", name = "enabled", havingValue = "true")
class PatientProfileRouteLocatorConfiguration {

    @Bean
    RouteLocator patientProfileLocator(
        final RouteLocatorBuilder builder,
        @Qualifier("default") final GatewayFilter globalFilter,
        final PatientProfileService parameters
    ) {
        return builder.routes()
            .route(
                "patient-profile",
                route -> route.order(Ordered.HIGHEST_PRECEDENCE)
                    .query("ContextAction", "ViewFile|FileSummary")
                    .filters(
                        filter -> filter.setPath("/nbs/redirect/patientProfile")
                            .filter(globalFilter)
                    )
                    .uri(parameters.uri())
            )
            .build();
    }
}
