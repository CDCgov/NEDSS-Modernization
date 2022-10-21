package gov.cdc.nbs.config.security;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.preauth.RequestHeaderAuthenticationFilter;

import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig {
    private final JWTFilter jwtFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http.authorizeRequests()
                .antMatchers("/graphiql", "/login", "/swagger-ui/**", "/swagger-resources/**", "/v2/api-docs")
                .permitAll()
                .anyRequest().authenticated()
                .and()
                .exceptionHandling().authenticationEntryPoint(this::writeErrorMessage)
                .and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
                .csrf().disable()
                .addFilterBefore(jwtFilter, RequestHeaderAuthenticationFilter.class)
                .build();
    }

    private void writeErrorMessage(
            HttpServletRequest req,
            HttpServletResponse res,
            AuthenticationException ex) throws IOException {
        if (ex instanceof InsufficientAuthenticationException) {
            res.setContentType("application/json;charset=UTF-8");
            res.setStatus(403);
            res.getWriter()
                    .write("{\"errors\": [{\"message\": \"Access denied. Please specify a valid 'Authorization' header. Ex: 'Authorization':'Bearer <token>'\"}] }");
        } else if (ex instanceof UsernameNotFoundException) {
            res.setContentType("application/json;charset=UTF-8");
            res.setStatus(401);
            res.getWriter().write("{\"error\": \"Invalid login\"}");
        }
    }
}
