package gov.cdc.nbs.patient.identifier;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.simple.JdbcClient;

@Configuration
class PatientIdentifierSettingsProvider {

  @Bean
  PatientIdentifierSettings defaultPatientIdentifierSettings(final JdbcClient client) {
    return new PatientIdentifierSettingsFinder(client).find();
  }

}
