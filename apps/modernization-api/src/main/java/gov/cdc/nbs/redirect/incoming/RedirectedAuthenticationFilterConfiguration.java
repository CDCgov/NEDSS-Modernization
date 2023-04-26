package gov.cdc.nbs.redirect.incoming;


import gov.cdc.nbs.AuthorizedUserResolver;
import gov.cdc.nbs.config.security.SecurityProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class RedirectedAuthenticationFilterConfiguration {

    /**
     * Creates and configures a {@code RedirectedAuthenticationFilter} that will be applied to incoming requests to any
     * `/nbs/redirect/` endpoint.
     */
    @Bean
    FilterRegistrationBean<RedirectedAuthenticationFilter> redirectedAuthenticationFilterRegistration(
        final AuthorizedUserResolver resolver,
        final SecurityProperties securityProperties
    ) {

        RedirectedAuthenticationFilter filter = new RedirectedAuthenticationFilter(resolver, securityProperties);

        FilterRegistrationBean<RedirectedAuthenticationFilter> registration =
            new FilterRegistrationBean<>(filter);
        registration.addUrlPatterns("/nbs/redirect/*");


        return registration;
    }
}
