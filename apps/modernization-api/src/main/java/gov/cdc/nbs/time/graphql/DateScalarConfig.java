package gov.cdc.nbs.time.graphql;

import graphql.schema.GraphQLScalarType;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.graphql.execution.RuntimeWiringConfigurer;

@Configuration
public class DateScalarConfig {

  @Bean
  public RuntimeWiringConfigurer dateScalarConfigurer() {

    return wiringBuilder ->
        wiringBuilder.scalar(
            GraphQLScalarType.newScalar()
                .name("Date")
                .description("Java LocalDate as scalar.")
                .coercing(new DateCoercing())
                .build());
  }
}
