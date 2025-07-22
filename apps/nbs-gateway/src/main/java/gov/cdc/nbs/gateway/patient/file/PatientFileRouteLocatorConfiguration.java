package gov.cdc.nbs.gateway.patient.file;

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
 * Configures the Patient File routes with a ContextAction of {@code ViewFile} or {@code FileSummary} to the
 * {@code nbs.gateway.patient.file.service}.  The routes are only enabled when the
 * {@code routes.patient.file.enabled} property is {@code true} and any of the following criteria is satisfied;
 *
 * <ul>
 *     <li>Query Parameter {@code ContextAction} of {@code ViewFile}</li>
 *     <li>Query Parameter {@code ContextAction} of {@code FileSummary}</li>
 * </ul>
 *
 * If the request contains a query parameter of either {@code uid} or {@code MPRUid} the request is routed to an endpoint
 * of the patient file service that can resolve the modernized patient file using the value of the query parameter.
 * Otherwise, the request is routed to an endpoint that can resolve the modernized patient file using contextual
 * request data present in cookies.
 */
@Configuration
@ConditionalOnProperty(prefix = "nbs.gateway.patient.file", name = "enabled", havingValue = "true")
class PatientFileRouteLocatorConfiguration {

  private static final String CONTEXT_ACTION_PARAMETER = "ContextAction";
  private static final String VALID_ACTIONS = "ViewFile|FileSummary";

  @Bean
  RouteLocator patientProfileLocator(
      final RouteLocatorBuilder builder,
      @Qualifier("defaults") final List<GatewayFilter> defaults,
      final PatientFileService service
  ) {
    return builder.routes()
        .route(
            "patient-file-direct-uid",
            route -> route
                .order(RouteOrdering.PATIENT_FILE.order())
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
            "patient-file-direct-uid",
            route -> route
                .order(RouteOrdering.PATIENT_FILE.order())
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
            "patient-file-resolving",
            route -> route
                .order(RouteOrdering.PATIENT_FILE.order())
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
