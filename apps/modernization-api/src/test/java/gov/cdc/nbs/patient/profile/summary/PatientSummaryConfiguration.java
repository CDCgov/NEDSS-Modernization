package gov.cdc.nbs.patient.profile.summary;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import gov.cdc.nbs.support.TestActive;

@Configuration
class PatientSummaryConfiguration {

    @Bean
    TestActive<PatientSummary> activePatientSummary() {
        return new TestActive<>();
    }
}
