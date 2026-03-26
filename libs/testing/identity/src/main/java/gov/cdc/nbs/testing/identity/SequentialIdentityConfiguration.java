package gov.cdc.nbs.testing.identity;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class SequentialIdentityConfiguration {

  @Bean
  SequentialIdentityGenerator.Options uniqueIdGeneratorOptions(
      @Value("${testing.id-generation.starting}") final long starting,
      @Value("${testing.id-generation.suffix}") final String suffix) {
    return new SequentialIdentityGenerator.Options(starting, suffix);
  }

  @Bean
  SequentialIdentityGenerator uniqueIdGenerator(final SequentialIdentityGenerator.Options options) {
    return new SequentialIdentityGenerator(options);
  }
}
