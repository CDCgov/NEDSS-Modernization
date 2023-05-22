package gov.cdc.nbs.redirect.incoming;


import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import gov.cdc.nbs.authentication.config.SecurityProperties;

@Configuration
class ClassicIncomingAuthenticationFilterConfiguration {

    /**
     * Creates and configures a {@code RedirectedAuthenticationFilter} that will be applied to incoming requests to any
     * `/nbs/redirect/` endpoint.
     */
    @Bean
    FilterRegistrationBean<ClassicIncomingAuthenticationFilter> classicIncomingAuthenticationFilterRegistration(
            final ClassicIncomingContextResolver resolver,
            final SecurityProperties securityProperties) {

        ClassicIncomingAuthenticationFilter filter =
                new ClassicIncomingAuthenticationFilter(resolver, securityProperties);

        FilterRegistrationBean<ClassicIncomingAuthenticationFilter> registration =
                new FilterRegistrationBean<>(filter);
        registration.addUrlPatterns("/nbs/redirect/*");


        return registration;
    }
}
