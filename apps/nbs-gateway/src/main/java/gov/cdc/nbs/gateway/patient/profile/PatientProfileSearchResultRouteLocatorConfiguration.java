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
 * Configures the Patient Profile Search Result routes with a ContextAction of {@code ViewFile} and a {@code uid} to the
 * {@code nbs.gateway.patient.profile.service}.  The routes are only enabled when the
 * {@code routes.patient.profile.enabled} property is {@code true} and any of the following criteria is satisfied;
 *
 * <ul>
 *     <li>Query Parameter {@code ContextAction} of {@code ViewFile}</li>
 *     <li>Query Parameter {@code uid} exists</li>
 * </ul>
 */
@Configuration
@ConditionalOnProperty(prefix = "nbs.gateway.patient.profile", name = "enabled", havingValue = "true")
class PatientProfileSearchResultRouteLocatorConfiguration {

  @Bean
  RouteLocator patientProfileSearchResultRouteLocator(
      final RouteLocatorBuilder builder,
      @Qualifier("defaults") final List<GatewayFilter> defaults,
      final PatientProfileService parameters
  ) {
    return builder.routes()
        .route(
            "patient-profile-search-result-redirect",
            route -> route
                .order(RouteOrdering.PATIENT_PROFILE.before())
                .path("/nbs/PatientSearchResults1.do")
                .and()
                .query("ContextAction", "ViewFile")
                .and()
                .query("uid")
                .filters(
                    filter -> filter.setPath("/nbs/redirect/patient/profile")
                        .filters(defaults)
                )
                .uri(parameters.uri())
        )
        .build();
  }
}
