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
 * Configures the Patient Profile routes with a ContextAction of {@code ViewFile} or {@code FileSummary} to the
 * {@code nbs.gateway.patient.profile.service}.  The routes are only enabled when the
 * {@code routes.patient.profile.enabled} property is {@code true} and any of the following criteria is satisfied;
 *
 * <ul>
 *     <li>Query Parameter {@code ContextAction} of {@code ViewFile}</li>
 *     <li>Query Parameter {@code ContextAction} of {@code FileSummary}</li>
 * </ul>
 *
 * If the request contains a query parameter of either {@code uid} or {@code MPRUid} the request is routed to an endpoint
 * of the patient profile service that can resolve the modernized patient profile using the value of the query parameter.
 * Otherwise, the request is routed to an endpoint that can resolve the modernized patient profile using contextual
 * request data present in cookies.
 */
@Configuration
@ConditionalOnProperty(prefix = "nbs.gateway.patient.profile", name = "enabled", havingValue = "true")
class PatientProfileRouteLocatorConfiguration {

  private static final String CONTEXT_ACTION_PARAMETER = "ContextAction";
  private static final String VALID_ACTIONS = "ViewFile|FileSummary";

  @Bean
  RouteLocator patientProfileLocator(
      final RouteLocatorBuilder builder,
      @Qualifier("defaults") final List<GatewayFilter> defaults,
      final PatientProfileService service
  ) {
    return builder.routes()
        .route(
            "patient-profile-direct-uid",
            route -> route
                .order(RouteOrdering.PATIENT_PROFILE.order())
                .query(CONTEXT_ACTION_PARAMETER, VALID_ACTIONS)
                .and()
                .query("uid")
                .filters(
                    filter -> filter.setPath(service.direct())
                        .filters(defaults)
                )
                .uri(service.uri())
        )
        .route(
            "patient-profile-direct-uid",
            route -> route
                .order(RouteOrdering.PATIENT_PROFILE.order())
                .query(CONTEXT_ACTION_PARAMETER, VALID_ACTIONS)
                .and()
                .query("MPRUid")
                .filters(
                    filter -> filter.setPath(service.direct())
                        .filters(defaults)
                )
                .uri(service.uri())
        )
        .route(
            "patient-profile-resolving",
            route -> route
                .order(RouteOrdering.PATIENT_PROFILE.order())
                .query(CONTEXT_ACTION_PARAMETER, VALID_ACTIONS)
                .filters(
                    filter -> filter.setPath(service.summary())
                        .filters(defaults)
                )
                .uri(service.uri())
        )
        .build();
  }
}
