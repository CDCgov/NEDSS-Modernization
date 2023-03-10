package gov.cdc.nbs.time.json;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Instant;

@Configuration
class JacksonJavaTimeConfiguration {

  @Bean
  Jackson2ObjectMapperBuilderCustomizer customizerForJavaTime() {
    return builder -> builder
        .serializationInclusion(JsonInclude.Include.NON_NULL)
        .modules(new JavaTimeModule())
        .serializerByType(Instant.class, new ISO8601InstantJsonSerializer())
        .deserializerByType(Instant.class, new ISO8601InstantJsonDeserializer());
  }
}
