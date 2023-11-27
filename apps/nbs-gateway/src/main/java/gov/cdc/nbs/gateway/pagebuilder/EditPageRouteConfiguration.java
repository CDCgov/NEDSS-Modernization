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
public class EditPageRouteConfiguration {

  @Bean
  RouteLocator pagebuilderEditPageConfig(
      final RouteLocatorBuilder builder,
      @Qualifier("default") final GatewayFilter globalFilter,
      final PageBuilderService service) {
    return builder.routes()
        .route(
            "pagebuilder-edit-page",
            route -> route
                .order(Ordered.HIGHEST_PRECEDENCE)
                .path("/nbs/ManagePage.do")
                .and()
                .query("method", "editPageContentsLoad")
                .filters(
                    filter -> filter.setPath("/nbs/page-builder/api/v1/pages/return")
                        .filter(globalFilter))
                .uri(service.uri()))
        .build();
  }
}
