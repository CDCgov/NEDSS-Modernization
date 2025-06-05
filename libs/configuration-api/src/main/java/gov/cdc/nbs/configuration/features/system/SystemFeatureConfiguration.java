package gov.cdc.nbs.configuration.features.system;

import gov.cdc.nbs.configuration.features.system.management.SystemManagement;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

@Configuration
class SystemFeatureConfiguration {

    @Bean
    @Scope("prototype")
    SystemManagement systemManagement(
            @Value("${nbs.ui.features.system.management.enabled:false}") final boolean enabled
    ) {
        return new SystemManagement(enabled);
    }

    @Bean
    @Scope("prototype")
    SystemFeatures systemFeatures(final SystemManagement management) {
        return new SystemFeatures(management);
    }
}

