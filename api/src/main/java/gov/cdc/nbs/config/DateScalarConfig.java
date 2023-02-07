package gov.cdc.nbs.config;

import java.time.Instant;
import java.time.format.DateTimeParseException;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.graphql.execution.RuntimeWiringConfigurer;

import gov.cdc.nbs.entity.enums.converter.InstantConverter;
import graphql.language.StringValue;
import graphql.schema.Coercing;
import graphql.schema.CoercingParseLiteralException;
import graphql.schema.CoercingParseValueException;
import graphql.schema.CoercingSerializeException;
import graphql.schema.GraphQLScalarType;

@Configuration
public class DateScalarConfig {

    @Bean
    public RuntimeWiringConfigurer runtimeWiringConfigurer() {
        var converter = new InstantConverter();

        var dateScalar = GraphQLScalarType.newScalar()
                .name("Date")
                .description("Java Instant as scalar.")
                .coercing(new Coercing<Instant, String>() {
                    @Override
                    public String serialize(final Object dataFetcherResult) {
                        if (dataFetcherResult instanceof Instant) {
                            return dataFetcherResult.toString();
                        } else {
                            throw new CoercingSerializeException("Expected a LocalDate object.");
                        }
                    }

                    @Override
                    public Instant parseValue(final Object input) {
                        try {
                            if (input instanceof String) {
                                return (Instant) converter.read(input);
                            } else {
                                throw new CoercingParseValueException("Expected a String");
                            }
                        } catch (DateTimeParseException e) {
                            throw new CoercingParseValueException(String.format("Not a valid date: '%s'.", input), e);
                        }
                    }

                    @Override
                    public Instant parseLiteral(final Object input) {
                        if (input instanceof StringValue) {
                            try {
                                return (Instant) converter.read(input.toString());
                            } catch (DateTimeParseException e) {
                                throw new CoercingParseLiteralException(e);
                            }
                        } else {
                            throw new CoercingParseLiteralException("Expected a StringValue.");
                        }
                    }
                }).build();

        return wiringBuilder -> wiringBuilder.scalar(dateScalar);

    }
}
