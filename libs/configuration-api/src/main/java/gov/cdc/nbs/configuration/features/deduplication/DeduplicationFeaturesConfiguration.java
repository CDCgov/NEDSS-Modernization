package gov.cdc.nbs.configuration.features.deduplication;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

@Configuration
class DeduplicationFeaturesConfiguration {

  @Bean
  @Scope("prototype")
  Deduplication deduplicationFeatures(
      @Value("${nbs.ui.features.deduplication.enabled:false}") final boolean enabled) {
    return new Deduplication(enabled);
  }
}
