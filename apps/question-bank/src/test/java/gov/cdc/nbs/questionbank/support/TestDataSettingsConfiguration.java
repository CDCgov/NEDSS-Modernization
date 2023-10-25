package gov.cdc.nbs.questionbank.support;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Instant;

@Configuration
class TestDataSettingsConfiguration {

  @Bean
  TestDataSettings testDataSettings(@Value("${testing.data.createdBy:99999}") final long createdBy) {
    return new TestDataSettings(createdBy, Instant.now());
  }

}
