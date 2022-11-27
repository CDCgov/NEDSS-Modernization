package gov.cdc.nbs.config;

import java.io.IOException;
import java.time.Instant;
import java.time.format.DateTimeParseException;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.graphql.execution.RuntimeWiringConfigurer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

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
    public ObjectMapper objectMapper() {
        JavaTimeModule javaTimeModule = new JavaTimeModule();
        var converter = new InstantConverter();
        javaTimeModule.addDeserializer(Instant.class, new StdDeserializer<Instant>(Instant.class) {
            @Override
            public Instant deserialize(JsonParser jsonParser, DeserializationContext deserializationContext)
                    throws IOException, JsonProcessingException {
                return (Instant) converter.read(jsonParser.readValueAs(String.class));
            }
        });
        javaTimeModule.addSerializer(Instant.class, new StdSerializer<Instant>(Instant.class) {

            @Override
            public void serialize(Instant value, JsonGenerator gen, SerializerProvider provider) throws IOException {
                gen.writeString((String) converter.write(value));
            }
        });
        var mapper = new ObjectMapper();
        mapper.registerModule(javaTimeModule);
        return mapper;
    }

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
