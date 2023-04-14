package gov.cdc.nbs.patient.identifier;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class PatientIdentifierSettingsProvider {

    private static final long DEFAULT_INITIAL = 10000000;

    @Bean
    @ConditionalOnProperty("nbs.identifier.person.initial")
    PatientIdentifierSettings patientIdentifierSettings(
        @Value("${nbs.identifier.person.initial}") final long initial,
        final PatientIdentifierSettingsResolver resolver
    ) {
        return resolver.resolve(initial);
    }

    @Bean
    @ConditionalOnMissingBean(PatientIdentifierSettings.class)
    PatientIdentifierSettings defaultPatientIdentifierSettings(final PatientIdentifierSettingsResolver resolver) {
        return resolver.resolve(DEFAULT_INITIAL);
    }

}
