package gov.cdc.nbs.configuration.features;

import gov.cdc.nbs.configuration.features.address.Address;
import gov.cdc.nbs.configuration.features.page_builder.PageBuilder;
import gov.cdc.nbs.configuration.features.patient.Patient;
import gov.cdc.nbs.configuration.features.search.Search;
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
      Patient patient
  ) {
    return () -> new Features(search, address, pageBuilder, patient);
  }

}