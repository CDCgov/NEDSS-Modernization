package gov.cdc.nbs.patient.profile;

import gov.cdc.nbs.message.patient.input.PatientInput;
import gov.cdc.nbs.support.Active;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class PatientProfileTestConfiguration {

    @Bean
    Active<PatientInput> activePatientInput() {
        return new Active<>();
    }
}
