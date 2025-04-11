package gov.cdc.nbs.configuration.features.patient;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

@Configuration
class PatientFeaturesConfiguration {

  @Bean
  @Scope("prototype")
  Patient patientFeatures(
      final Patient.Search search,
      final Patient.Profile profile,
      final Patient.Add add,
      final Patient.File file) {
    return new Patient(search, profile, add, file);
  }

  @Bean
  @Scope("prototype")
  Patient.Search patientSearch(
      @Value("${nbs.ui.features.patient.search.filters.enabled:false}") final boolean enabled) {
    return new Patient.Search(
        new Patient.Search.Filters(enabled));
  }

  @Bean
  @Scope("prototype")
  Patient.Profile patientProfileFeatures(
      @Value("${nbs.ui.features.patient.profile.enabled:false}") final boolean enabled) {
    return new Patient.Profile(enabled);
  }

  @Bean
  @Scope("prototype")
  Patient.File patientFileFeatures(@Value("${nbs.ui.features.patient.file.enabled:false}") final boolean enabled) {
    return new Patient.File(enabled);
  }

  @Bean
  @Scope("prototype")
  Patient.Add patientAddFeatures(
      @Value("${nbs.ui.features.patient.add.enabled:false}") final boolean enabled,
      @Value("${nbs.ui.features.patient.add.extended.enabled:false}") final boolean extendedEnabled) {
    return new Patient.Add(enabled, new Patient.Add.Extended(extendedEnabled));
  }
}
