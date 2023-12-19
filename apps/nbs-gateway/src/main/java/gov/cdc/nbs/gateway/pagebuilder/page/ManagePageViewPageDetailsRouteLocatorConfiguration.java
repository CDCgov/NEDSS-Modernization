package gov.cdc.nbs.gateway.pagebuilder.page;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import gov.cdc.nbs.gateway.pagebuilder.PageBuilderService;

@Configuration
@ConditionalOnExpression("${nbs.gateway.pagebuilder.enabled} and ${nbs.gateway.pagebuilder.page.management.enabled}")
public class ManagePageViewPageDetailsRouteLocatorConfiguration {

  @Bean
  RouteLocator pageBuilderManagePageViewPageDetailsRouteLocator(
      final RouteLocatorBuilder builder,
      @Qualifier("default") final GatewayFilter globalFilter,
      final PageBuilderService service
  ) {
    return builder.routes()
        .route(
            "page-builder-manage-page-view-page-details-return",
            route -> route
                .order(Ordered.HIGHEST_PRECEDENCE)
                .path("/nbs/ManagePage.do")
                .and()
                .query("method", "viewPageDetailsLoad")
                .and()
                .cookie("Return-Page", "\\d+")
                .filters(
                    filter -> filter.setPath(service.path("pages/return"))
                        .filter(globalFilter)
                )
                .uri(service.uri()))
        .build();
  }
}
