package gov.cdc.nbs.graphql;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.ExceptionHandlingConfigurer;
import org.springframework.security.web.servlet.util.matcher.PathPatternRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.stereotype.Component;

@Component
public class GraphQLExceptionHandlingConfigurer {

  private final ObjectMapper mapper;
  private final RequestMatcher matcher;

  public GraphQLExceptionHandlingConfigurer(
      final ObjectMapper mapper, @Value("${spring.graphql.http.path}") final String endpoint) {
    this.mapper = mapper;
    this.matcher = PathPatternRequestMatcher.withDefaults().matcher(endpoint);
  }

  public void configure(final ExceptionHandlingConfigurer<HttpSecurity> exceptions) {

    GraphQLSecurityExceptionAdapter adapter = new GraphQLSecurityExceptionAdapter(mapper);

    exceptions.defaultAuthenticationEntryPointFor(adapter.authenticationEntryPoint(), matcher);
  }
}
