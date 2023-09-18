package gov.cdc.nbs.redirect.incoming;


import gov.cdc.nbs.authentication.config.SecurityProperties;
import gov.cdc.nbs.authentication.token.NBSTokenCookieEnsurer;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class ClassicIncomingAuthenticationFilterConfiguration {

    /**
     * Creates and configures a {@code RedirectedAuthenticationFilter} that will be applied to incoming requests to any
     * `/nbs/redirect/` endpoint.
     */
    @Bean
    FilterRegistrationBean<ClassicIncomingAuthenticationFilter> classicIncomingAuthenticationFilterRegistration(
        final ClassicIncomingContextResolver resolver,
        final NBSTokenCookieEnsurer ensurer,
        final SecurityProperties securityProperties
    ) {

        ClassicIncomingAuthenticationFilter filter =
            new ClassicIncomingAuthenticationFilter(resolver, ensurer, securityProperties);

        FilterRegistrationBean<ClassicIncomingAuthenticationFilter> registration =
            new FilterRegistrationBean<>(filter);

        registration.addUrlPatterns("/nbs/redirect/*");


        return registration;
    }
}
