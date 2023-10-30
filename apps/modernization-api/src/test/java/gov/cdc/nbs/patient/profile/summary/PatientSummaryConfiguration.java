package gov.cdc.nbs.patient.profile.summary;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import gov.cdc.nbs.testing.support.Active;

@Configuration
class PatientSummaryConfiguration {

    @Bean
    Active<PatientSummary> activePatientSummary() {
        return new Active<>();
    }
}
