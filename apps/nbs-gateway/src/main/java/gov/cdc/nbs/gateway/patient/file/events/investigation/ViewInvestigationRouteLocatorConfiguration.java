package gov.cdc.nbs.gateway.patient.file.events.investigation;

import gov.cdc.nbs.gateway.classic.NBSClassicService;
import gov.cdc.nbs.gateway.filter.RemoveCookieGatewayFilterFactory;
import gov.cdc.nbs.gateway.filter.RequestParameterToCookieGatewayFilterFactory;
import gov.cdc.nbs.gateway.filter.RequestParameterToCookieGatewayFilterFactory.Config;
import java.util.List;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Adds the {@code Patient-Action} cookie to the response when the {@code
 * routes.patient.profile.enabled} property is {@code true} and any of the following criteria is
 * satisfied; The {@code Return-Patient} cookie is also removed.
 *
 * <ul>
 *   <li>Path equal to {@code /nbs/MyProgramAreaInvestigations1.do}*
 *   <li>Query Parameter {@code publicHealthCaseUID} exists
 * </ul>
 *
 * <ul>
 *   <li>Path equal to {@code /nbs/ViewFile1.do}
 *   <li>Query Parameter {@code ContextAction} equal to {@code }
 *   <li>Query Parameter {@code publicHealthCaseUID} exists
 * </ul>
 */
@Configuration
@ConditionalOnProperty(prefix = "nbs.gateway.patient.file", name = "enabled", havingValue = "true")
class ViewInvestigationRouteLocatorConfiguration {

  private static final String IDENTIFIER_PARAMETER = "publicHealthCaseUID";
  private static final Config ADD_PATIENT_ACTION_CONFIG =
      new Config(IDENTIFIER_PARAMETER, "Patient-Action");
  private static final RemoveCookieGatewayFilterFactory.Config REMOVE_RETURN_PATIENT_CONFIG =
      new RemoveCookieGatewayFilterFactory.Config("/nbs", "Return-Patient");

  @Bean
  RouteLocator viewInvestigationActionCookie(
      final RouteLocatorBuilder builder,
      @Qualifier("defaults") final List<GatewayFilter> defaults,
      @Qualifier("classic") final GatewayFilter classicFilter,
      final NBSClassicService service) {
    return builder
        .routes()
        .route(
            "view-open-investigations-apply-patient-action-cookie",
            route ->
                route
                    .path("/nbs/MyProgramAreaInvestigations1.do", "/nbs/PatientSearchResults1.do")
                    .and()
                    .query(IDENTIFIER_PARAMETER)
                    .filters(
                        filter ->
                            filter
                                .filter(
                                    new RequestParameterToCookieGatewayFilterFactory()
                                        .apply(ADD_PATIENT_ACTION_CONFIG))
                                .filter(
                                    new RemoveCookieGatewayFilterFactory()
                                        .apply(REMOVE_RETURN_PATIENT_CONFIG))
                                .filters(defaults)
                                .filter(classicFilter))
                    .uri(service.uri()))
        .route(
            "view-investigation-apply-patient-action-cookie",
            route ->
                route
                    .path("/nbs/ViewFile1.do")
                    .and()
                    .query("ContextAction", "InvestigationIDOnSummary")
                    .and()
                    .query(IDENTIFIER_PARAMETER)
                    .filters(
                        filter ->
                            filter
                                .filter(
                                    new RequestParameterToCookieGatewayFilterFactory()
                                        .apply(ADD_PATIENT_ACTION_CONFIG))
                                .filter(
                                    new RemoveCookieGatewayFilterFactory()
                                        .apply(REMOVE_RETURN_PATIENT_CONFIG))
                                .filters(defaults)
                                .filter(classicFilter))
                    .uri(service.uri()))
        .build();
  }
}
