package gov.cdc.nbs.questionbank.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import gov.cdc.nbs.authentication.NBSAuthenticationFilter;
import gov.cdc.nbs.authentication.NBSAuthenticationIssuer;
import gov.cdc.nbs.authentication.session.SessionAuthenticator;
import gov.cdc.nbs.authentication.token.NBSTokenValidator;
import lombok.RequiredArgsConstructor;


@Configuration
@RequiredArgsConstructor
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
@ConditionalOnProperty("nbs.security.enabled")
@EnableJpaRepositories({"gov.cdc.nbs.questionbank"})
@EntityScan({"gov.cdc.nbs.questionbank"})
public class WebSecurityConfig {

  @Bean
  @SuppressWarnings("squid:S4502")
  public SecurityFilterChain securityFilterChain(
      HttpSecurity http,
      final NBSTokenValidator tokenValidator,
      final PageBuilderAuthenticationIgnoredPaths ignoredPaths,
      final NBSAuthenticationIssuer authIssuer,
      final SessionAuthenticator sessionAuthenticator) throws Exception {

    final NBSAuthenticationFilter authFilter = new NBSAuthenticationFilter(
        tokenValidator,
        ignoredPaths,
        authIssuer,
        sessionAuthenticator);

    return http.authorizeRequests()
        .antMatchers(ignoredPaths.toArray())
        .permitAll()
        .anyRequest()
        .authenticated()
        .and()
        .csrf()
        .disable()
        .addFilterBefore(authFilter, UsernamePasswordAuthenticationFilter.class)
        .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        .and()
        .build();
  }



}
