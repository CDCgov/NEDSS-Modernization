package gov.cdc.nbs.questionbank.support;

import java.time.Instant;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class TestDataSettingsConfiguration {

  @Bean
  TestDataSettings testDataSettings(
      @Value("${testing.data.createdBy:99999}") final long createdBy) {
    return new TestDataSettings(createdBy, Instant.now());
  }
}
