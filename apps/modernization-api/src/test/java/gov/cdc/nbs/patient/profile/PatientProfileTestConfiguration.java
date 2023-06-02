package gov.cdc.nbs.patient.profile;

import gov.cdc.nbs.message.patient.input.PatientInput;
import gov.cdc.nbs.support.TestActive;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class PatientProfileTestConfiguration {

    @Bean
    TestActive<PatientInput> activePatientInput() {
        return new TestActive<>();
    }
}
