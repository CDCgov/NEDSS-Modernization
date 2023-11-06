package gov.cdc.nbs.gateway.pagebuilder;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;

@Configuration
@ConditionalOnProperty(prefix = "nbs.gateway.pagebuilder", name = "enabled", havingValue = "true")
public class ManagePagesRouteConfiguration {
  @Bean
  RouteLocator pagebuilderManagePagesConfig(
      final RouteLocatorBuilder builder,
      @Qualifier("default") final GatewayFilter globalFilter,
      final PageBuilderService service) {
    return builder.routes()
        .route(
            "pagebuilder-manage-pages",
            route -> route
                .order(Ordered.HIGHEST_PRECEDENCE)
                .path("/nbs/ManagePage.do")
                .and()
                .query("method", "list")
                .and()
                .query("initLoad", "true")
                .filters(
                    filter -> filter.setPath("/nbs/redirect")
                        .setRequestHeader("NBS_REDIRECT", "/page-builder/manage/pages")
                        .filter(globalFilter))
                .uri(service.uri()))
        .build();
  }
}
