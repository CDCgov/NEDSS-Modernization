package gov.cdc.nbs.gateway;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.RewriteLocationResponseHeaderGatewayFilterFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Routes specified via the Java DSL do not include any default-filters defined with properties.  This configuration
 * provides a Java DSL equivalent of the default-filter defined in the configuration so that the default filters can be
 * included if needed.
 */
@Configuration
class DSLGlobalGatewayFilterConfiguration {

    @Bean
    @Qualifier("default")
    GatewayFilter defaultGatewayFilter() {
        return new RewriteLocationResponseHeaderGatewayFilterFactory().apply(
            new RewriteLocationResponseHeaderGatewayFilterFactory.Config());
    }

}
