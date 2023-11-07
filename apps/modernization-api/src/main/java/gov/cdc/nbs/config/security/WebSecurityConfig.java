package gov.cdc.nbs.config.security;

import java.io.IOException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import com.fasterxml.jackson.databind.ObjectMapper;
import gov.cdc.nbs.authentication.IgnoredPaths;
import gov.cdc.nbs.authentication.NBSAuthenticationFilter;
import gov.cdc.nbs.authentication.NBSAuthenticationIssuer;
import gov.cdc.nbs.authentication.session.SessionAuthenticator;
import gov.cdc.nbs.authentication.token.NBSTokenValidator;
import graphql.GraphQLError;
import graphql.GraphqlErrorBuilder;
import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
public class WebSecurityConfig {
  private final ObjectMapper mapper;

  @Value("${spring.graphql.path:/graphql}")
  private String graphQLEndpoint;

  @Bean
  public SecurityFilterChain securityFilterChain(
      HttpSecurity http,
      final NBSTokenValidator tokenValidator,
      final NBSAuthenticationIssuer authIssuer,
      final SessionAuthenticator sessionAuthenticator)
      throws Exception {

    final IgnoredPaths ignoredPaths = new IgnoredPaths(
        "/v3/api-docs/**",
        "/swagger-ui/**",
        "/swagger-resources/**",
        "/v2/api-docs/**",
        "/login");

    final NBSAuthenticationFilter authFilter = new NBSAuthenticationFilter(
        tokenValidator,
        ignoredPaths,
        authIssuer,
        sessionAuthenticator);
    return http.authorizeRequests()
        .antMatchers(ignoredPaths.paths())
        .permitAll()
        .anyRequest().authenticated()
        .and()
        .csrf().disable()
        .addFilterBefore(authFilter, UsernamePasswordAuthenticationFilter.class)
        .exceptionHandling().authenticationEntryPoint(this::writeErrorMessage)
        .and()
        .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        .and()
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
      if (req.getRequestURI().contains(graphQLEndpoint)) {
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
