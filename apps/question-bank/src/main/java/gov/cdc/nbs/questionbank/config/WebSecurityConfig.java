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
import org.springframework.security.web.authentication.preauth.RequestHeaderAuthenticationFilter;
import gov.cdc.nbs.authentication.JWTFilter;
import lombok.RequiredArgsConstructor;


@Configuration
@RequiredArgsConstructor
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
@ConditionalOnProperty("nbs.security.enabled")
@EnableJpaRepositories({"gov.cdc.nbs.questionbank"})
@EntityScan({"gov.cdc.nbs.questionbank"})
public class WebSecurityConfig {
    private final JWTFilter jwtFilter;


    @Bean
    @SuppressWarnings("squid:S4502")
    // Stateless applications implementing Bearer JWT scheme are protected against CSRF
    // https://www.baeldung.com/spring-security-csrf#:~:text=If%20our%20stateless%20API%20uses,as%20we'll%20see%20next.
    // https://docs.spring.io/spring-security/reference/features/exploits/csrf.html#csrf-protection-ssa
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http.authorizeRequests()
                .antMatchers("/v3/api-docs/**", "/swagger-ui/**", "/swagger-resources/**", "/v2/api-docs/**")
                .permitAll()
                .anyRequest().authenticated()
                .and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .csrf()
                .disable()
                .addFilterBefore(jwtFilter, RequestHeaderAuthenticationFilter.class)
                .build();
    }



}
