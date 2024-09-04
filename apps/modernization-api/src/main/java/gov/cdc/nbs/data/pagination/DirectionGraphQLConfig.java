package gov.cdc.nbs.data.pagination;

import graphql.schema.GraphQLScalarType;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.graphql.execution.RuntimeWiringConfigurer;

@Configuration
class DirectionGraphQLConfig {

  @Bean
  public RuntimeWiringConfigurer directionScalarConfigurer() {
    return wiringBuilder -> wiringBuilder
        .scalar(
        GraphQLScalarType.newScalar()
            .name("Direction")
            .description("Sort.Direction as scalar.")
            .coercing(new DirectionCoercing())
            .build()
    );
  }
}
