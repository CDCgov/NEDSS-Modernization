package gov.cdc.nbs.gateway.deduplication;

import java.util.List;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;

import gov.cdc.nbs.gateway.ui.UIService;

/**
 * Configures the NBS System Identified merge page to route to the modernized
 * patient merge landing page. The route is enabled when the
 * {@code nbs.gateway.deduplication.merge.enabled} property is {@code true}.
 */
@Configuration
@ConditionalOnProperty(prefix = "nbs.gateway.deduplication.merge", name = "enabled", havingValue = "true")
public class MergeLandingPageRouteLocatorConfiguration {

  @Bean
  RouteLocator systemIdentifiedRouteLocator(
      final RouteLocatorBuilder builder,
      @Qualifier("defaults") final List<GatewayFilter> defaults,
      final UIService uiService) {
    return builder.routes()
        .route("system-identified-merge",
            route -> route.order(Ordered.HIGHEST_PRECEDENCE)
                .path("/nbs/LoadMergeCandidateList2.do")
                .and()
                .query("ContextAction", "GlobalMP_SystemIndentified")
                .filters(
                    filters -> filters.redirect(302, "/deduplication/merge"))
                .uri(uiService.uri()))
        .build();
  }
}
