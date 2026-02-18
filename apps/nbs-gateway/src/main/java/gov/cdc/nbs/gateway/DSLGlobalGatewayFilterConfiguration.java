package gov.cdc.nbs.gateway;

import java.util.ArrayList;
import java.util.List;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.RewriteLocationResponseHeaderGatewayFilterFactory;
import org.springframework.cloud.gateway.filter.factory.TokenRelayGatewayFilterFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

/**
 * Routes specified via the Java DSL do not include any default-filters defined with properties.
 * This configuration provides a Java DSL equivalent of the default-filter defined in the
 * configuration so that the default filters can be included if needed.
 */
@Configuration
class DSLGlobalGatewayFilterConfiguration {

  @Bean("defaults")
  List<GatewayFilter> defaultGatewayFilters(
      final Environment environment, final ApplicationContext context) {

    List<GatewayFilter> defaults = new ArrayList<>();

    defaults.add(
        new RewriteLocationResponseHeaderGatewayFilterFactory()
            .apply(new RewriteLocationResponseHeaderGatewayFilterFactory.Config()));

    if (environment.matchesProfiles("oidc")) {
      defaults.add(context.getBean(TokenRelayGatewayFilterFactory.class).apply());
    }

    return defaults;
  }
}
