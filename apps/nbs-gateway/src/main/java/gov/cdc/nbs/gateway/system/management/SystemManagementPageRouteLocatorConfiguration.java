package gov.cdc.nbs.gateway.system.management;

import gov.cdc.nbs.gateway.ui.UIService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;

import java.util.List;

/**
 * Configures the NBS System Management page to route to the modernized
 * system management page. The route is enabled when the
 * {@code nbs.gateway.system.management.enabled} property is {@code true}.
 */

@Configuration
@ConditionalOnProperty(prefix = "nbs.gateway.system.management", name = "enabled", havingValue = "true")
public class SystemManagementPageRouteLocatorConfiguration {
    @Bean
    RouteLocator systemManagementRouteLocator(
            final RouteLocatorBuilder builder,
            @Qualifier("defaults") final List<GatewayFilter> defaults,
            final UIService uiService) {

        return builder.routes()
                .route("system-management",
                        route -> route.order(Ordered.HIGHEST_PRECEDENCE)
                                .path("/nbs/SystemAdmin.do")
                                .filters(filters -> filters.redirect(302, "/system/management"))
                                .uri(uiService.uri()))
                .build();
    }
}
