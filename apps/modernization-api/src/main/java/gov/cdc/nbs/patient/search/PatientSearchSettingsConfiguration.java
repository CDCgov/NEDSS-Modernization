package gov.cdc.nbs.patient.search;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class PatientSearchSettingsConfiguration {

  @Bean
  PatientSearchSettings patientSearchSettings(
      final @Value("${nbs.patient.search.name.first.boost.primary:10}") float firstNamePrimaryBoost,
      final @Value("${nbs.patient.search.name.first.boost.nonPrimary:1}") float
              firstNameNonPrimaryBoost,
      final @Value("${nbs.patient.search.name.first.boost.soundex:0}") float firstNameSoundexBoost,
      final @Value("${nbs.patient.search.name.last.boost.primary:10}") float lastNamePrimaryBoost,
      final @Value("${nbs.patient.search.name.last.boost.nonPrimary:1}") float
              lastNameNonPrimaryBoost,
      final @Value("${nbs.patient.search.name.last.boost.soundex:0}") float lastNameSoundexBoost) {
    return new PatientSearchSettings(
        new PatientSearchSettings.NameBoost(
            firstNamePrimaryBoost, firstNameNonPrimaryBoost, firstNameSoundexBoost),
        new PatientSearchSettings.NameBoost(
            lastNamePrimaryBoost, lastNameNonPrimaryBoost, lastNameSoundexBoost));
  }
}
