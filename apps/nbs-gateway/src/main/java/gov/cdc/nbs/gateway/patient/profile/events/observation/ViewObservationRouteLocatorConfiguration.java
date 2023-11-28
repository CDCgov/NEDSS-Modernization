package gov.cdc.nbs.gateway.patient.profile.events.observation;

import gov.cdc.nbs.gateway.classic.NBSClassicService;
import gov.cdc.nbs.gateway.filter.RequestParameterToCookieGatewayFilterFactory;
import gov.cdc.nbs.gateway.filter.RequestParameterToCookieGatewayFilterFactory.Config;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Adds the {@code Patient-Action} cookie to the response when the {@code routes.patient.profile.enabled} property is {@code true}
 * and any of the following criteria is satisfied;
 *
 * <ul>
 *     <li>Path equal to {@code /nbs/NewLabReview1.do.do}</li>*
 *     <li>Query Parameter {@code observationUID} exists</li>
 * </ul>
 */
@Configuration
@ConditionalOnProperty(prefix = "nbs.gateway.patient.profile", name = "enabled", havingValue = "true")
class ViewObservationRouteLocatorConfiguration {

    private static final String IDENTIFIER_PARAMETER = "observationUID";

    @Bean
    RouteLocator viewObservationActionCookie(
            final RouteLocatorBuilder builder,
            @Qualifier("default") final GatewayFilter defaultFilter,
            @Qualifier("classic") final GatewayFilter classicFilter,
            final NBSClassicService service
    ) {
        return builder
                .routes()
                .route(
                        "view-observation-apply-patient-action-cookie",
                        route -> route.path("/nbs/NewLabReview1.do")
                                .and()
                                .query(IDENTIFIER_PARAMETER)
                                .filters(
                                        filter -> filter.filter(
                                                        new RequestParameterToCookieGatewayFilterFactory()
                                                                .apply(
                                                                        new Config(
                                                                            IDENTIFIER_PARAMETER,
                                                                                "Patient-Action"
                                                                        )
                                                                )
                                                )
                                                .filter(defaultFilter)
                                                .filter(classicFilter)
                                )
                                .uri(service.uri())
                )
                .build();
    }

}
