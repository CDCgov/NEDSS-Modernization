package gov.cdc.nbs.identity;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class TestIdentityModule {

  @Bean
  TestUniqueIdGenerator uniqueIdGenerator(final @Value("${testing.id-generation.starting}") long starting) {
    return new TestUniqueIdGenerator(starting);
  }

}
