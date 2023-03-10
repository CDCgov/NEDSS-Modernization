package gov.cdc.nbs.time.graphql;

import graphql.schema.GraphQLScalarType;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.graphql.execution.RuntimeWiringConfigurer;

@Configuration
public class DateScalarConfig {

  @Bean
  public RuntimeWiringConfigurer runtimeWiringConfigurer() {

    var dateScalar = GraphQLScalarType.newScalar()
        .name("Date")
        .description("Java Instant as scalar.")
        .coercing(new ISO8601InstantCoercing()).build();

    return wiringBuilder -> wiringBuilder.scalar(dateScalar);

  }
}
