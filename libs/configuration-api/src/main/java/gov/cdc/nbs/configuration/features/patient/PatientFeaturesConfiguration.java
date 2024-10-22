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
      @Value("${nbs.ui.features.patient.add.enabled:false}") final boolean enabled,
      @Value("${nbs.ui.features.patient.add.extended.enabled:false}") final boolean extendedEnabled
  ) {
    return new Patient(new Patient.Add(enabled, new Patient.Add.Extended(extendedEnabled)));
  }

}
