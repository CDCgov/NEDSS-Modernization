package gov.cdc.nbs.config.security;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Value;
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

import com.fasterxml.jackson.databind.ObjectMapper;

import graphql.GraphQLError;
import graphql.GraphqlErrorBuilder;
import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig {
    private final JWTFilter jwtFilter;
    private final ObjectMapper mapper;

    @Value("${spring.graphql.path: graphql}")
    private String graphQLEndpoint;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http.authorizeRequests()
                .antMatchers("/graphql")
                .authenticated()
                .anyRequest().permitAll()
                .and()
                .exceptionHandling().authenticationEntryPoint(this::writeErrorMessage)
                .and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
                .csrf().disable()
                .addFilterBefore(jwtFilter, RequestHeaderAuthenticationFilter.class)
                .build();
    }

    /**
     * Writes an authentication error message in the format expected by GraphQL
     * clients
     */
    private void writeErrorMessage(
            HttpServletRequest req,
            HttpServletResponse res,
            AuthenticationException ex) throws IOException {
        if (ex instanceof InsufficientAuthenticationException && req.getRequestURI().contains(graphQLEndpoint)) {
            res.setContentType("application/json;charset=UTF-8");
            res.setStatus(403);
            GraphQLError error = GraphqlErrorBuilder
                    .newError()
                    .message(
                            "Access denied. Please specify a valid 'Authorization' header. Ex: 'Authorization':'Bearer <token>'")
                    .build();
            res.getWriter().write(mapper.writeValueAsString(error));
        } else if (ex instanceof UsernameNotFoundException) {
            res.setContentType("application/json;charset=UTF-8");
            res.setStatus(401);
        }
    }
}
