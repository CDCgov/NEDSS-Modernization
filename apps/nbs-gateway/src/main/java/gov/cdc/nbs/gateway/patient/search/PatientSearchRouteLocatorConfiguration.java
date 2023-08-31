package gov.cdc.nbs.gateway.patient.search;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;

/**
 * Configures the NBS Home Page to route searches and the advanced search link to the patient-search service.  The
 * routes are only enabled when the {@code routes.patient.search.enabled} property is {@code true} and any of the
 * following criteria is satisfied.
 *
 * <ul>
 *     <li>Path equal to {@code /nbs/MyTaskList1.do}</li>
 *     <li>Query Parameter {@code ContextAction} equal to {@code GlobalPatient}</li>
 * </ul>
 *
 * <ul>
 *     <li>Path equal to {@code /nbs/HomePage.do}</li>
 *     <li>Query Parameter {@code method} equal to {@code patientSearchSubmit}</li>
 * </ul>
 */
@Configuration
@ConditionalOnProperty(prefix = "nbs.gateway.patient.search", name = "enabled", havingValue = "true")
class PatientSearchRouteLocatorConfiguration {

    @Bean
    RouteLocator searchRouteLocator(
        final RouteLocatorBuilder builder,
        @Qualifier("default") final GatewayFilter globalFilter,
        final PatientSearchService parameters
    ) {
        return builder.routes()
            .route(
                "advanced-search-link",
                route -> route
                    .order(Ordered.HIGHEST_PRECEDENCE)
                    .path("/nbs/MyTaskList1.do")
                    .and()
                    .query("ContextAction", "GlobalPatient")
                    .filters(
                        filter -> filter.setPath("/nbs/redirect/advancedSearch")
                            .filter(globalFilter)
                    )
                    .uri(parameters.uri())
            )
            .route("home-page-search",
                route -> route.order(Ordered.HIGHEST_PRECEDENCE)
                    .path("/nbs/HomePage.do")
                    .and()
                    .query("method", "patientSearchSubmit")
                    .filters(
                        filter -> filter.setPath("/nbs/redirect/simpleSearch")
                            .filter(globalFilter)
                    )
                    .uri(parameters.uri())
            )
            .build();
    }
}
