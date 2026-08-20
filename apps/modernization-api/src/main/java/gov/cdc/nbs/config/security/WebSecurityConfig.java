package gov.cdc.nbs.config.security;

import gov.cdc.nbs.authentication.SecurityConfigurer;
import gov.cdc.nbs.graphql.GraphQLExceptionHandlingConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(securedEnabled = true)
class WebSecurityConfig {

  @Bean
  SecurityFilterChain securityFilterChain(
      final HttpSecurity http,
      final SecurityConfigurer configurer,
      final GraphQLExceptionHandlingConfigurer graphQLConfigurer)
      throws Exception {
    return configurer.configure(http.exceptionHandling(graphQLConfigurer::configure)).build();
  }
}
