package gov.cdc.nbs.patient.identifier;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@Slf4j
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
        log.warn(
            "An initial seed value for Person was not provided, the default {} will be used.  If a specific value is required make sure the nbs.identifier.person.initial property is set.",
            DEFAULT_INITIAL
        );
        return resolver.resolve(DEFAULT_INITIAL);
    }

}
