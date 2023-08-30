package gov.cdc.nbs.gateway.patient.profile.events.investigation;

import gov.cdc.nbs.gateway.patient.profile.PatientProfileService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;

/**
 * Configures the Patient Profile routes to the {@code /nbs/redirect/patient/investigation/merge} path of the
 * {@code nbs.gateway.patient.profile.service} when the {@code routes.patient.profile.enabled} property is {@code true}
 * and any of the following criteria is satisfied;
 *
 * <ul>
 *     <li>Path equal to {@code /nbs/PageAction.do}</li>
 *     <li>Query Parameter {@code method} equal to {@code mergeSubmit}</li>
 *     <li>Query Parameter {@code ContextAction} equal to {@code Submit}</li>
 * </ul>
 */
@Configuration
@ConditionalOnProperty(prefix = "nbs.gateway.patient.profile", name = "enabled", havingValue = "true")
class MergedInvestigationLocatorConfiguration {

    @Bean
    RouteLocator mergedInvestigationPatientProfileReturn(
        final RouteLocatorBuilder builder,
        @Qualifier("default") final GatewayFilter globalFilter,
        final PatientProfileService parameters
    ) {
        return builder.routes()
            .route(
                "merged-investigation-patient-profile-return",
                route -> route.order(Ordered.HIGHEST_PRECEDENCE)
                    .path("/nbs/PageAction.do")
                    .and()
                    .query("ContextAction", "Submit")
                    .and()
                    .query("method", "mergeSubmit")
                    .filters(
                        filter -> filter.setPath("/nbs/redirect/patient/investigation/merge")
                            .filter(globalFilter)
                    )
                    .uri(parameters.uri())
            )
            .build();
    }

}
