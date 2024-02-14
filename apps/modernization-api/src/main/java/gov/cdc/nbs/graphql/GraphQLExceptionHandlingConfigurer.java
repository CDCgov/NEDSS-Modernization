package gov.cdc.nbs.graphql;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.ExceptionHandlingConfigurer;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.stereotype.Component;

@Component
public class GraphQLExceptionHandlingConfigurer {

  private final ObjectMapper mapper;
  private final RequestMatcher matcher;

  public GraphQLExceptionHandlingConfigurer(
      final ObjectMapper mapper,
      @Value("${spring.graphql.path}") final String endpoint
  ) {
    this.mapper = mapper;
    this.matcher = new AntPathRequestMatcher(endpoint);
  }

  public void configure(final ExceptionHandlingConfigurer<HttpSecurity> exceptions) {

    GraphQLSecurityExceptionAdapter adapter =
        new GraphQLSecurityExceptionAdapter(mapper);

    exceptions.defaultAuthenticationEntryPointFor(
        adapter.authenticationEntryPoint(),
        matcher
    );
  }

}
