package gov.cdc.nbs.config;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoField;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.graphql.execution.RuntimeWiringConfigurer;

import graphql.language.StringValue;
import graphql.schema.Coercing;
import graphql.schema.CoercingParseLiteralException;
import graphql.schema.CoercingParseValueException;
import graphql.schema.CoercingSerializeException;
import graphql.schema.GraphQLScalarType;

@Configuration
public class DateScalarConfig {
    private final DateTimeFormatter formatter = new DateTimeFormatterBuilder()
            .appendOptional(DateTimeFormatter.ofPattern("MM/dd/yyyy"))
            .appendOptional(DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss.SSS"))
            .appendOptional(DateTimeFormatter.ISO_INSTANT)
            .parseDefaulting(ChronoField.HOUR_OF_DAY, 0)
            .parseDefaulting(ChronoField.MINUTE_OF_HOUR, 0)
            .parseDefaulting(ChronoField.SECOND_OF_MINUTE, 0)
            .toFormatter().withZone(ZoneOffset.UTC);

    @Bean
    public RuntimeWiringConfigurer runtimeWiringConfigurer() {
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
                                return LocalDateTime.parse((String) input, formatter).toInstant(ZoneOffset.UTC);
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
                                return LocalDateTime.parse(((StringValue) input).getValue(), formatter)
                                        .toInstant(ZoneOffset.UTC);
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
