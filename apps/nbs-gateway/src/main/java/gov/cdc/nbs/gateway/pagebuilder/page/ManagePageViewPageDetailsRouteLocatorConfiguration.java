package gov.cdc.nbs.gateway.pagebuilder.page;

import gov.cdc.nbs.gateway.pagebuilder.PageBuilderService;
import java.util.List;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;

@Configuration
@ConditionalOnExpression(
    "${nbs.gateway.pagebuilder.enabled} and ${nbs.gateway.pagebuilder.page.management.edit.enabled}")
public class ManagePageViewPageDetailsRouteLocatorConfiguration {

  @Bean
  RouteLocator pageBuilderManagePageViewPageDetailsRouteLocator(
      final RouteLocatorBuilder builder,
      @Qualifier("defaults") final List<GatewayFilter> defaults,
      final PageBuilderService service) {
    return builder
        .routes()
        .route(
            "page-builder-manage-page-view-page-details-return",
            route ->
                route
                    .order(Ordered.HIGHEST_PRECEDENCE)
                    .path("/nbs/ManagePage.do")
                    .and()
                    .query("method", "viewPageDetailsLoad")
                    .and()
                    .cookie("Return-Page", "\\d+")
                    .filters(
                        filter -> filter.setPath(service.path("pages/return")).filters(defaults))
                    .uri(service.uri()))
        .build();
  }
}
