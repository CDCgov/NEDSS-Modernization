package gov.cdc.nbs.questionbank.config;

import java.io.IOException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.http.HttpStatus;
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
import gov.cdc.nbs.authentication.JWTFilter;
import graphql.GraphQLError;
import graphql.GraphqlErrorBuilder;
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
    private final ObjectMapper mapper;

    @Value("${spring.graphql.path: graphql}")
    private String graphQLEndpoint;

    @Bean
    @SuppressWarnings("squid:S4502")
    // Stateless applications implementing Bearer JWT scheme are protected against CSRF
    // https://www.baeldung.com/spring-security-csrf#:~:text=If%20our%20stateless%20API%20uses,as%20we'll%20see%20next.
    // https://docs.spring.io/spring-security/reference/features/exploits/csrf.html#csrf-protection-ssa
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http.authorizeRequests()
                .antMatchers("/graphiql")
                .permitAll()
                .anyRequest().authenticated()
                .and()
                .exceptionHandling().authenticationEntryPoint(this::writeErrorMessage)
                .and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .csrf()
                .ignoringAntMatchers("/graphiql", "/graphql")
                .and()
                .addFilterBefore(jwtFilter, RequestHeaderAuthenticationFilter.class)
                .build();
    }

    /**
     * Writes an authentication error message in the format expected by GraphQL clients
     */
    private void writeErrorMessage(
            HttpServletRequest req,
            HttpServletResponse res,
            AuthenticationException ex) throws IOException {
        if (ex instanceof InsufficientAuthenticationException || ex instanceof UsernameNotFoundException) {
            // If graphql endpoint, write graphql error	
            if (req.getRequestURI().contains(graphQLEndpoint) ||
                    req.getAttribute("javax.servlet.forward.request_uri").equals(graphQLEndpoint)) {
                res.setContentType("application/json;charset=UTF-8");
                GraphQLError error = GraphqlErrorBuilder
                        .newError()
                        .message(
                                "Access denied. Please specify a valid 'Authorization' header. Ex: 'Authorization':'Bearer <token>'")
                        .build();
                res.getWriter().write(mapper.writeValueAsString(error));
            }
            // set unauthorized status	
            res.setStatus(HttpStatus.UNAUTHORIZED.value());
        }
    }

}
