package gov.cdc.nbs.configuration.features.deduplication;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import gov.cdc.nbs.configuration.features.deduplication.Deduplication.Merge;

@Configuration
class DeduplicationFeaturesConfiguration {

  @Bean
  @Scope("prototype")
  Deduplication deduplicationFeatures(
      @Value("${nbs.ui.features.deduplication.enabled:false}") final boolean enabled, Merge merge) {
    return new Deduplication(enabled, merge);
  }

  @Bean
  @Scope("prototype")
  Merge deduplicationMergeFeature(
      @Value("${nbs.ui.features.deduplication.merge.enabled:false}") final boolean enabled) {
    return new Merge(enabled);
  }
}
