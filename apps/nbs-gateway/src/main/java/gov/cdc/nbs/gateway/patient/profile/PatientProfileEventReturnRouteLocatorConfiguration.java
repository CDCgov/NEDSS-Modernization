package gov.cdc.nbs.gateway.patient.profile;

import gov.cdc.nbs.gateway.RouteOrdering;
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
 * {@code nbs.gateway.patient.profile.service} when the {@code routes.patient.profile.enabled} property is {@code true}
 * and the following criteria is satisfied;
 *
 * <ul>
 *     <li>Query Parameter {@code ContextAction} of {@code ReturnToFileEvents}</li>
 * </ul>
 */
@Configuration
@ConditionalOnProperty(prefix = "nbs.gateway.patient.profile", name = "enabled", havingValue = "true")
class PatientProfileEventReturnRouteLocatorConfiguration {

    @Bean
    RouteLocator patientProfileEventsReturnLocator(
        final RouteLocatorBuilder builder,
        @Qualifier("defaults") final List<GatewayFilter> defaults,
        final PatientProfileService parameters
    ) {
        return builder.routes()
            .route(
                "patient-profile-event-return",
                route -> route
                    .order(RouteOrdering.PATIENT_PROFILE.order())
                    .query("ContextAction", "ReturnToFileEvents")
                    .filters(
                        filter -> filter.setPath(parameters.events())
                            .filters(defaults)
                    )
                    .uri(parameters.uri())
            )
            .build();
    }
}
