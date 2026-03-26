package gov.cdc.nbs.time.graphql;

import graphql.schema.GraphQLScalarType;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.graphql.execution.RuntimeWiringConfigurer;

@Configuration
public class DateTimeScalarConfig {

  @Bean
  public RuntimeWiringConfigurer dateTimeScalarConfigurer() {
    return wiringBuilder ->
        wiringBuilder.scalar(
            GraphQLScalarType.newScalar()
                .name("DateTime")
                .description("Java Instant as scalar.")
                .coercing(new ISO8601InstantCoercing())
                .build());
  }
}
