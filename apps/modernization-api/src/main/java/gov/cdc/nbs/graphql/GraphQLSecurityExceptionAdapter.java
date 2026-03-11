package gov.cdc.nbs.graphql;

import com.fasterxml.jackson.databind.ObjectMapper;
import graphql.GraphQLError;
import graphql.GraphqlErrorBuilder;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

class GraphQLSecurityExceptionAdapter {

  private final ObjectMapper mapper;

  GraphQLSecurityExceptionAdapter(final ObjectMapper mapper) {
    this.mapper = mapper;
  }

  AuthenticationEntryPoint authenticationEntryPoint() {
    return (final HttpServletRequest request,
        final HttpServletResponse response,
        final AuthenticationException exception) ->
        failure(response, "Access denied. %s".formatted(exception.getMessage()));
  }

  private void failure(final HttpServletResponse response, final String reason) throws IOException {
    GraphQLError error = GraphqlErrorBuilder.newError().message(reason).build();

    response.setContentType(MediaType.APPLICATION_GRAPHQL_RESPONSE_VALUE);
    response.setStatus(HttpStatus.UNAUTHORIZED.value());
    response.getWriter().write(mapper.writeValueAsString(error));
  }
}
