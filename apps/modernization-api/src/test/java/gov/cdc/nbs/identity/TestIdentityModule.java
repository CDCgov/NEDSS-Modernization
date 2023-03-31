package gov.cdc.nbs.identity;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class TestIdentityModule {

  @Bean
  MotherSettings motherSettings(final @Value("${testing.id-generation.starting}") long starting) {
    return new MotherSettings(
        starting,
        "TEST",
        9999L
    );
  }

  @Bean
  TestUniqueIdGenerator uniqueIdGenerator(final MotherSettings settings) {
    return new TestUniqueIdGenerator(settings);
  }

}
