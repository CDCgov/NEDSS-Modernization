package gov.cdc.nbs.config;

import org.springframework.boot.autoconfigure.graphql.GraphQlSourceBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GraphQLConfig {

  @Bean
  // Sets our GraphQLExceptionHanlder as the default exception handler
  public GraphQlSourceBuilderCustomizer sourceBuilderCustomizer() {
    return builder ->
        builder.configureGraphQl(
            graphQlBuilder ->
                graphQlBuilder.defaultDataFetcherExceptionHandler(new GraphQLExceptionHandler()));
  }
}
