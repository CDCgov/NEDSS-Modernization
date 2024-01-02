package gov.cdc.nbs.patient.profile.race.change;

import gov.cdc.nbs.message.patient.input.RaceInput;
import gov.cdc.nbs.testing.support.Active;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class PatientProfileRaceChangeConfiguration {

  @Bean
  Active<RaceInput> raceInputActive() {
    return new Active<>();
  }

  @Bean
  Active<DeletePatientRace> deletePatientRaceActive() {
    return new Active<>();
  }
}
