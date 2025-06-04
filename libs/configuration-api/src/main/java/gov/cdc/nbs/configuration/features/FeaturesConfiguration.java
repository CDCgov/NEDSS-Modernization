package gov.cdc.nbs.configuration.features;

import gov.cdc.nbs.configuration.features.address.Address;
import gov.cdc.nbs.configuration.features.deduplication.Deduplication;
import gov.cdc.nbs.configuration.features.page_builder.PageBuilder;
import gov.cdc.nbs.configuration.features.patient.Patient;
import gov.cdc.nbs.configuration.features.search.Search;
import gov.cdc.nbs.configuration.features.system_management.SystemManagement;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.annotation.RequestScope;

@Configuration
class FeaturesConfiguration {

  @Bean
  @RequestScope
  FeaturesResolver featuresResolver(
      Search search,
      Address address,
      PageBuilder pageBuilder,
      Deduplication deduplication,
      Patient patient,
      SystemManagement systemManagement) {
    return () -> new Features(
        search,
        address,
        pageBuilder,
        deduplication,
        patient,
        systemManagement);
  }

}
