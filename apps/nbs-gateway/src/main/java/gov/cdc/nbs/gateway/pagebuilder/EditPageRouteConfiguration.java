package gov.cdc.nbs.gateway.pagebuilder;

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
public class EditPageRouteConfiguration {

  @Bean
  RouteLocator pagebuilderEditPageConfig(
      final RouteLocatorBuilder builder,
      @Qualifier("defaults") final List<GatewayFilter> defaults,
      final PageBuilderService service) {
    return builder
        .routes()
        .route(
            "pagebuilder-edit-page",
            route ->
                route
                    .order(Ordered.HIGHEST_PRECEDENCE)
                    .path("/nbs/ManagePage.do")
                    .and()
                    .query("method", "editPageContentsLoad")
                    .filters(
                        filter ->
                            filter
                                .setPath("/nbs/page-builder/api/v1/pages/return")
                                .filters(defaults))
                    .uri(service.uri()))
        .build();
  }
}
